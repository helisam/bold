package destaxa.autorizador.util;

import destaxa.autorizador.dto.TransacaoRequestDTO;
import destaxa.autorizador.dto.TransacaoResponseDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Utilitário para conversão entre mensagens ISO8583 e objetos DTO
 * 
 * Esta classe implementa métodos para converter mensagens no formato ISO8583
 * para objetos DTO e vice-versa, facilitando o processamento de transações
 * financeiras no sistema autorizador.
 */
public class ISO8583Parser {

    private static final Logger logger = LoggerFactory.getLogger(ISO8583Parser.class);

    /**
     * Converte uma mensagem ISO8583 em um objeto TransacaoRequestDTO
     * 
     * @param mensagemISO8583 Mensagem no formato ISO8583
     * @return Objeto TransacaoRequestDTO com os dados da mensagem
     * @throws IllegalArgumentException se a mensagem for nula, vazia ou em formato inválido
     * @throws NumberFormatException se o valor da transação não for um número válido
     */
    public static TransacaoRequestDTO parseFromISO8583(String mensagemISO8583) {
        logger.debug("Iniciando conversão de mensagem ISO8583 para objeto");
        
        // Validação de entrada
        if (mensagemISO8583 == null || mensagemISO8583.trim().isEmpty()) {
            logger.error("Mensagem ISO8583 nula ou vazia");
            throw new IllegalArgumentException("Mensagem ISO8583 não pode ser nula ou vazia");
        }
        
        // Implementação simplificada para fins de demonstração
        // Em um cenário real, seria necessário um parser completo de ISO8583
        TransacaoRequestDTO request = new TransacaoRequestDTO();
        
        // Exemplo de parsing básico (simulado)
        String[] campos = mensagemISO8583.split("\\|");
        
        if (campos.length >= 10) {
            try {
                request.setCodigoMensagem(getValorCampo(campos, 0));
                request.setNumeroCartao(getValorCampo(campos, 1));
                request.setCodigoProcessamento(getValorCampo(campos, 2));
                
                String valorStr = getValorCampo(campos, 3);
                if (valorStr.isEmpty()) {
                    logger.error("Valor da transação não informado");
                    throw new IllegalArgumentException("Valor da transação não informado");
                }
                request.setValor(Double.parseDouble(valorStr));
                
                request.setDataTransacao(getValorCampo(campos, 4));
                request.setHoraTransacao(getValorCampo(campos, 5));
                request.setNsu(getValorCampo(campos, 6));
                request.setCodigoEstabelecimento(getValorCampo(campos, 7));
                request.setNsuHost(getValorCampo(campos, 8));
                request.setModoEntrada(getValorCampo(campos, 9));
                
                if (campos.length > 10) {
                    request.setCodigoSeguranca(getValorCampo(campos, 10));
                }
                
                if (campos.length > 11) {
                    request.setParcelas(getValorCampo(campos, 11));
                }
            } catch (NumberFormatException e) {
                logger.error("Erro ao converter valor da transação: {}", e.getMessage());
                throw new NumberFormatException("Valor da transação inválido: " + e.getMessage());
            } catch (Exception e) {
                logger.error("Erro ao processar mensagem ISO8583: {}", e.getMessage());
                throw new IllegalArgumentException("Erro ao processar mensagem ISO8583: " + e.getMessage());
            }
        } else {
            logger.error("Formato de mensagem ISO8583 inválido: {}", mensagemISO8583);
            throw new IllegalArgumentException("Formato de mensagem ISO8583 inválido. A mensagem deve conter pelo menos 10 campos.");
        }
        
        // Validação adicional dos campos obrigatórios
        if (request.getCodigoMensagem().isEmpty() || request.getNumeroCartao().isEmpty() || 
            request.getNsu().isEmpty() || request.getCodigoEstabelecimento().isEmpty()) {
            logger.error("Campos obrigatórios não informados na mensagem ISO8583");
            throw new IllegalArgumentException("Campos obrigatórios não informados na mensagem ISO8583");
        }
        
        logger.debug("Conversão de mensagem ISO8583 para objeto concluída");
        return request;
    }

    /**
     * Converte um objeto TransacaoResponseDTO em uma mensagem ISO8583
     * 
     * @param response Objeto TransacaoResponseDTO com os dados da resposta
     * @return Mensagem no formato ISO8583
     * @throws IllegalArgumentException se o objeto response for nulo ou contiver campos obrigatórios não preenchidos
     */
    public static String parseToISO8583(TransacaoResponseDTO response) {
        logger.debug("Iniciando conversão de objeto para mensagem ISO8583");
        
        // Validação de entrada
        if (response == null) {
            logger.error("Objeto TransacaoResponseDTO nulo");
            throw new IllegalArgumentException("Objeto TransacaoResponseDTO não pode ser nulo");
        }
        
        // Validação de campos obrigatórios
        if (response.getCodigoMensagem() == null || response.getCodigoMensagem().isEmpty() ||
            response.getCodigoResposta() == null || response.getCodigoResposta().isEmpty() ||
            response.getNsu() == null || response.getNsu().isEmpty() ||
            response.getCodigoEstabelecimento() == null || response.getCodigoEstabelecimento().isEmpty()) {
            
            logger.error("Campos obrigatórios não preenchidos no objeto TransacaoResponseDTO");
            throw new IllegalArgumentException("Campos obrigatórios não preenchidos no objeto TransacaoResponseDTO");
        }
        
        try {
            // Implementação simplificada para fins de demonstração
            StringBuilder mensagem = new StringBuilder();
            
            // Montagem da mensagem ISO8583 (formato simplificado)
            mensagem.append(response.getCodigoMensagem() != null ? response.getCodigoMensagem() : "").append("|");
            mensagem.append(formatarValor(response.getValor())).append("|");
            mensagem.append(response.getDataTransacao() != null ? response.getDataTransacao() : "").append("|");
            mensagem.append(response.getHoraTransacao() != null ? response.getHoraTransacao() : "").append("|");
            mensagem.append(response.getNsu() != null ? response.getNsu() : "").append("|");
            mensagem.append(response.getCodigoAutorizacao() != null ? response.getCodigoAutorizacao() : "").append("|");
            mensagem.append(response.getCodigoResposta() != null ? response.getCodigoResposta() : "").append("|");
            mensagem.append(response.getCodigoEstabelecimento() != null ? response.getCodigoEstabelecimento() : "").append("|");
            mensagem.append(response.getNsuHost() != null ? response.getNsuHost() : "");
            
            String resultado = mensagem.toString();
            logger.debug("Conversão de objeto para mensagem ISO8583 concluída: {}", resultado);
            return resultado;
        } catch (Exception e) {
            logger.error("Erro ao converter objeto para mensagem ISO8583: {}", e.getMessage());
            throw new IllegalArgumentException("Erro ao converter objeto para mensagem ISO8583: " + e.getMessage());
        }
    }
    
    /**
     * Obtém o valor de um campo da mensagem ISO8583
     * 
     * @param campos Array de campos da mensagem
     * @param indice Índice do campo desejado
     * @return Valor do campo ou string vazia se o índice for inválido
     */
    private static String getValorCampo(String[] campos, int indice) {
        if (campos == null) {
            logger.warn("Array de campos nulo ao tentar obter valor do campo {}", indice);
            return "";
        }
        
        if (indice >= 0 && indice < campos.length) {
            String valor = campos[indice];
            return valor != null ? valor.trim() : "";
        }
        
        logger.warn("Tentativa de acessar índice inválido: {} (tamanho do array: {})", indice, campos.length);
        return "";
    }
    
    /**
     * Formata um valor monetário para string
     * 
     * @param valor Valor a ser formatado
     * @return Valor formatado como string
     */
    private static String formatarValor(double valor) {
        try {
            return String.format("%.2f", valor);
        } catch (Exception e) {
            logger.error("Erro ao formatar valor monetário: {}", e.getMessage());
            return "0.00";
        }
    }
}
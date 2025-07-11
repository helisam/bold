package destaxa.apipagamento.util;

import destaxa.apipagamento.dto.AuthorizationRequest;
import destaxa.apipagamento.dto.AuthorizationResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * Utilitário para conversão entre objetos DTO e mensagens ISO8583
 */
public class ISO8583Helper {

    private static final Logger logger = LoggerFactory.getLogger(ISO8583Helper.class);
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("MMdd");
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HHmmss");

    /**
     * Converte um objeto AuthorizationRequest em uma mensagem ISO8583
     * 
     * @param request Objeto AuthorizationRequest com os dados da requisição
     * @return Mensagem no formato ISO8583
     */
    public static String toISO8583(AuthorizationRequest request) {
        logger.debug("Iniciando conversão de objeto para mensagem ISO8583");
        
        // Implementação simplificada para fins de demonstração
        StringBuilder mensagem = new StringBuilder();
        
        // Código da mensagem (0200 para solicitação financeira)
        mensagem.append("0200").append("|");
        
        // Número do cartão (simulado)
        mensagem.append("4111111111111111").append("|");
        
        // Código de processamento (00 para compra)
        mensagem.append("00").append("|");
        
        // Valor da transação
        mensagem.append(String.format("%.2f", request.getValue())).append("|");
        
        // Data da transação (formato MMDD)
        mensagem.append(LocalDate.now().format(DATE_FORMATTER)).append("|");
        
        // Hora da transação (formato HHMMSS)
        mensagem.append(LocalTime.now().format(TIME_FORMATTER)).append("|");
        
        // NSU (Número Sequencial Único)
        mensagem.append(request.getExternalId()).append("|");
        
        // Código do estabelecimento
        mensagem.append("123456789").append("|");
        
        // NSU do host
        mensagem.append(generateNsuHost()).append("|");
        
        // Modo de entrada (01 para digitado)
        mensagem.append("01").append("|");
        
        // Código de segurança (simulado)
        mensagem.append("123").append("|");
        
        // Parcelas
        mensagem.append(request.getInstallments());
        
        logger.debug("Conversão de objeto para mensagem ISO8583 concluída");
        return mensagem.toString();
    }

    /**
     * Converte uma mensagem ISO8583 em um objeto AuthorizationResponse
     * 
     * @param mensagemISO8583 Mensagem no formato ISO8583
     * @return Objeto AuthorizationResponse com os dados da resposta
     */
    public static AuthorizationResponse fromISO8583(String mensagemISO8583) {
        logger.debug("Iniciando conversão de mensagem ISO8583 para objeto");
        
        // Implementação simplificada para fins de demonstração
        AuthorizationResponse response = new AuthorizationResponse();
        
        // Exemplo de parsing básico (simulado)
        String[] campos = mensagemISO8583.split("\\|");
        
        if (campos.length >= 9) {
            // Ignorar código da mensagem (índice 0)
            
            // Valor da transação
            response.setValue(Double.parseDouble(getValorCampo(campos, 1)));
            
            // Data da transação
            response.setTransactionDate(getValorCampo(campos, 2));
            
            // Hora da transação
            response.setTransactionHour(getValorCampo(campos, 3));
            
            // NSU (Número Sequencial Único) como paymentId
            response.setPaymentId(getValorCampo(campos, 4));
            
            // Código de autorização
            response.setAuthorizationCode(getValorCampo(campos, 5));
            
            // Código de resposta
            response.setResponseCode(getValorCampo(campos, 6));
            
            // Ignorar código do estabelecimento (índice 7)
            
            // Ignorar NSU do host (índice 8)
        } else {
            logger.error("Formato de mensagem ISO8583 inválido: {}", mensagemISO8583);
            throw new IllegalArgumentException("Formato de mensagem ISO8583 inválido");
        }
        
        logger.debug("Conversão de mensagem ISO8583 para objeto concluída");
        return response;
    }
    
    /**
     * Obtém o valor de um campo da mensagem ISO8583
     * 
     * @param campos Array de campos da mensagem
     * @param indice Índice do campo desejado
     * @return Valor do campo ou string vazia se o índice for inválido
     */
    private static String getValorCampo(String[] campos, int indice) {
        if (indice >= 0 && indice < campos.length) {
            return campos[indice];
        }
        return "";
    }
    
    /**
     * Gera um NSU do host aleatório
     * 
     * @return NSU do host gerado
     */
    private static String generateNsuHost() {
        return String.format("%06d", (int) (Math.random() * 1000000));
    }
}
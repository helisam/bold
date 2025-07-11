package destaxa.apipagamento.service;

import destaxa.apipagamento.client.AutorizadorClient;
import destaxa.apipagamento.dto.AuthorizationRequest;
import destaxa.apipagamento.dto.AuthorizationResponse;
import destaxa.apipagamento.exception.TransactionProcessingException;
import destaxa.apipagamento.util.ISO8583Helper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class PagamentoService {

    private static final Logger logger = LoggerFactory.getLogger(PagamentoService.class);
    private final AutorizadorClient autorizadorClient;

    public PagamentoService(AutorizadorClient autorizadorClient) {
        this.autorizadorClient = autorizadorClient;
        logger.info("PagamentoService inicializado");
    }

    public AuthorizationResponse processarAutorizacao(AuthorizationRequest request) {
        logger.info("Iniciando processamento de autorização para NSU: {}", request.getExternalId());
        
        // Validar valor máximo
        if (request.getValue() > 1000.00) {
            logger.warn("Transação com valor acima do limite permitido: {}", request.getValue());
            throw new TransactionProcessingException("Timeout simulado para valores acima de R$1000,00");
        }
        
        // Converter para mensagem ISO8583
        logger.debug("Convertendo requisição para formato ISO8583");
        String mensagemISO8583 = ISO8583Helper.toISO8583(request);
        logger.debug("Mensagem ISO8583 gerada: {}", mensagemISO8583);
        
        // Enviar para o autorizador
        logger.info("Enviando mensagem para o autorizador");
        String respostaISO8583 = autorizadorClient.enviarParaAutorizador(mensagemISO8583);
        logger.debug("Resposta ISO8583 recebida: {}", respostaISO8583);
        
        // Converter resposta
        logger.debug("Convertendo resposta do formato ISO8583");
        AuthorizationResponse response = ISO8583Helper.fromISO8583(respostaISO8583);
        
        logger.info("Processamento de autorização concluído para NSU: {} com código de resposta: {}", 
                 request.getExternalId(), response.getResponseCode());
        return response;
    }
}
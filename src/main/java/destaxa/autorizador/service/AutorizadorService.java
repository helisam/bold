package destaxa.autorizador.service;

import destaxa.autorizador.dto.TransacaoRequestDTO;
import destaxa.autorizador.dto.TransacaoResponseDTO;
import destaxa.autorizador.exception.TransactionProcessingException;
import destaxa.autorizador.util.ISO8583Parser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class AutorizadorService {

    private static final Logger logger = LoggerFactory.getLogger(AutorizadorService.class);

    public String processarMensagem(String mensagemISO8583) {
        logger.info("Recebida mensagem ISO8583 para processamento");
        logger.debug("Mensagem recebida: {}", mensagemISO8583);
        
        logger.debug("Convertendo mensagem ISO8583 para objeto de requisição");
        TransacaoRequestDTO request = ISO8583Parser.parseFromISO8583(mensagemISO8583);
        logger.debug("Requisição convertida: {}", request);
        
        logger.info("Processando transação para NSU: {}", request.getNsu());
        TransacaoResponseDTO response = processarTransacao(request);
        logger.debug("Resposta gerada: {}", response);
        
        logger.debug("Convertendo resposta para formato ISO8583");
        String responseISO8583 = ISO8583Parser.parseToISO8583(response);
        logger.debug("Resposta ISO8583: {}", responseISO8583);
        
        logger.info("Processamento concluído com código de resposta: {}", response.getCodigoResposta());
        return responseISO8583;
    }

    private TransacaoResponseDTO processarTransacao(TransacaoRequestDTO request) {
        logger.debug("Iniciando processamento de transação com valor: {}", request.getValor());
        TransacaoResponseDTO response = new TransacaoResponseDTO();
        
        if (request.getValor() > 1000.00) {
            logger.warn("Transação com valor acima do limite permitido: {}", request.getValor());
            throw new TransactionProcessingException("Timeout simulado para valores acima de R$1000,00. Limite máximo permitido: R$1000,00");
        }

        response.setCodigoMensagem("0210");
        response.setValor(request.getValor());
        response.setNsu(request.getNsu());
        response.setDataTransacao(request.getDataTransacao());
        response.setHoraTransacao(request.getHoraTransacao());
        response.setCodigoEstabelecimento(request.getCodigoEstabelecimento());
        response.setNsuHost(request.getNsuHost());

        if (request.getValor() % 2 == 0) {
            logger.info("Transação aprovada para NSU: {}", request.getNsu());
            response.setCodigoResposta("000");
            response.setCodigoAutorizacao(generateAuthorizationCode());
            logger.debug("Código de autorização gerado: {}", response.getCodigoAutorizacao());
        } else {
            logger.info("Transação recusada para NSU: {}", request.getNsu());
            response.setCodigoResposta("051");
            response.setCodigoAutorizacao("000000");
        }

        return response;
    }

    private String generateAuthorizationCode() {
        String code = String.format("%06d", (int) (Math.random() * 1000000));
        logger.debug("Código de autorização gerado: {}", code);
        return code;
    }
}
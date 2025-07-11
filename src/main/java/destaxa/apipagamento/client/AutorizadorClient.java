package destaxa.apipagamento.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Cliente para comunicação com o serviço autorizador via socket
 */
@Component
public class AutorizadorClient {

    private static final Logger logger = LoggerFactory.getLogger(AutorizadorClient.class);
    
    @Value("${autorizador.host:localhost}")
    private String autorizadorHost;
    
    @Value("${autorizador.socket.port:9999}")
    private int autorizadorPort;
    
    @Value("${autorizador.socket.timeout:5000}")
    private int socketTimeout;

    /**
     * Envia uma mensagem ISO8583 para o autorizador e retorna a resposta
     * 
     * @param mensagemISO8583 Mensagem no formato ISO8583
     * @return Resposta do autorizador no formato ISO8583
     */
    public String enviarParaAutorizador(String mensagemISO8583) {
        logger.debug("Conectando ao autorizador em {}:{}", autorizadorHost, autorizadorPort);
        
        try (Socket socket = new Socket(autorizadorHost, autorizadorPort)) {
            socket.setSoTimeout(socketTimeout);
            
            // Enviar mensagem
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            out.println(mensagemISO8583);
            logger.debug("Mensagem enviada para o autorizador");
            
            // Receber resposta
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String resposta = in.readLine();
            logger.debug("Resposta recebida do autorizador");
            
            return resposta;
        } catch (IOException e) {
            logger.error("Erro ao comunicar com o autorizador: {}", e.getMessage());
            throw new RuntimeException("Erro ao comunicar com o autorizador", e);
        }
    }
}
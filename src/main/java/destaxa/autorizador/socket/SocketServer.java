package destaxa.autorizador.socket;

import destaxa.autorizador.dto.TransacaoResponseDTO;
import destaxa.autorizador.service.AutorizadorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Servidor de socket para receber e processar mensagens ISO8583
 */
public class SocketServer {

    private static final Logger logger = LoggerFactory.getLogger(SocketServer.class);

    @Value("${autorizador.socket.port}")
    private int socketPort;

    @Value("${autorizador.socket.pool-size}")
    private int poolSize;

    private final AutorizadorService autorizadorService;
    private ServerSocket serverSocket;
    private ExecutorService executorService;
    private boolean running = false;

    public SocketServer(AutorizadorService autorizadorService) {
        this.autorizadorService = autorizadorService;
    }

    public void start() {
        try {
            serverSocket = new ServerSocket(socketPort);
            executorService = Executors.newFixedThreadPool(poolSize);
            running = true;

            logger.info("Servidor de socket iniciado na porta {}", socketPort);

            // Iniciar thread para aceitar conexões
            new Thread(this::acceptConnections).start();

        } catch (IOException e) {
            logger.error("Erro ao iniciar servidor de socket: {}", e.getMessage(), e);
        }
    }

    private void acceptConnections() {
        while (running) {
            try {
                Socket clientSocket = serverSocket.accept();
                executorService.submit(() -> handleClient(clientSocket));
            } catch (IOException e) {
                if (running) {
                    logger.error("Erro ao aceitar conexão: {}", e.getMessage(), e);
                }
            }
        }
    }

    private void handleClient(Socket clientSocket) {
        try {
            logger.debug("Nova conexão aceita de {}", clientSocket.getInetAddress());

            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);

            String mensagemISO8583 = in.readLine();
            logger.debug("Mensagem recebida: {}", mensagemISO8583);

            if (mensagemISO8583 != null && !mensagemISO8583.isEmpty()) {
                try {
                    String resposta = autorizadorService.processarMensagem(mensagemISO8583);
                    out.println(resposta);
                    logger.debug("Resposta enviada: {}", resposta);
                } catch (destaxa.autorizador.exception.TransactionProcessingException e) {
                    // Criar uma resposta de erro para transações com valor acima do limite
                    logger.warn("Erro no processamento da transação: {}", e.getMessage());
                    
                    // Extrair os dados da mensagem original para construir a resposta
                    String[] campos = mensagemISO8583.split("\\|");
                    String valor = campos.length > 3 ? campos[3] : "0.00";
                    String nsu = campos.length > 6 ? campos[6] : "UNKNOWN";
                    String codigoEstabelecimento = campos.length > 7 ? campos[7] : "123456789";
                    
                    // Construir resposta de erro com os dados da transação original
                    TransacaoResponseDTO errorResponse = new TransacaoResponseDTO();
                    errorResponse.setCodigoMensagem("0210");
                    errorResponse.setValor(Double.parseDouble(valor));
                    errorResponse.setDataTransacao(java.time.LocalDate.now().format(java.time.format.DateTimeFormatter.ofPattern("MMdd")));
                    errorResponse.setHoraTransacao(java.time.LocalTime.now().format(java.time.format.DateTimeFormatter.ofPattern("HHmmss")));
                    errorResponse.setNsu(nsu);
                    errorResponse.setCodigoAutorizacao("000000"); // Código de autorização padrão para erro
                    errorResponse.setCodigoResposta("051"); // Código de resposta para transação recusada
                    errorResponse.setCodigoEstabelecimento(codigoEstabelecimento);
                    errorResponse.setNsuHost(String.format("%06d", (int) (Math.random() * 1000000)));
                    
                    // Converter para formato ISO8583 usando o parser
                    String respostaErro = destaxa.autorizador.util.ISO8583Parser.parseToISO8583(errorResponse);
                    out.println(respostaErro);
                    logger.debug("Resposta de erro enviada: {}", respostaErro);
                }
            }

        } catch (IOException e) {
            logger.error("Erro ao processar cliente: {}", e.getMessage(), e);
        } finally {
            try {
                clientSocket.close();
                logger.debug("Conexão fechada");
            } catch (IOException e) {
                logger.error("Erro ao fechar conexão: {}", e.getMessage(), e);
            }
        }
    }

    public void stop() {
        running = false;
        if (executorService != null) {
            executorService.shutdown();
        }
        if (serverSocket != null && !serverSocket.isClosed()) {
            try {
                serverSocket.close();
                logger.info("Servidor de socket encerrado");
            } catch (IOException e) {
                logger.error("Erro ao encerrar servidor de socket: {}", e.getMessage(), e);
            }
        }
    }
}
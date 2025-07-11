package destaxa.autorizador.config;

import destaxa.autorizador.service.AutorizadorService;
import destaxa.autorizador.socket.SocketServer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuração do servidor de socket
 */
@Configuration
public class SocketServerConfig {

    private final AutorizadorService autorizadorService;

    public SocketServerConfig(AutorizadorService autorizadorService) {
        this.autorizadorService = autorizadorService;
    }

    /**
     * Configura o servidor de socket como um bean gerenciado pelo Spring
     * 
     * @return Instância do servidor de socket
     */
    @Bean(initMethod = "start", destroyMethod = "stop")
    public SocketServer socketServer() {
        return new SocketServer(autorizadorService);
    }
}
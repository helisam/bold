package destaxa.apipagamento.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ExceptionsTest {

    @Test
    void businessException_ConstrutorComMensagem_DeveInicializarCorretamente() {
        // Arrange & Act
        String mensagem = "Erro de negócio";
        BusinessException exception = new BusinessException(mensagem);

        // Assert
        assertNotNull(exception);
        assertEquals(mensagem, exception.getMessage());
    }

    @Test
    void businessException_ConstrutorComMensagemECausa_DeveInicializarCorretamente() {
        // Arrange & Act
        String mensagem = "Erro de negócio";
        Throwable causa = new RuntimeException("Causa original");
        BusinessException exception = new BusinessException(mensagem, causa);

        // Assert
        assertNotNull(exception);
        assertEquals(mensagem, exception.getMessage());
        assertEquals(causa, exception.getCause());
    }

    @Test
    void resourceNotFoundException_ConstrutorComMensagem_DeveInicializarCorretamente() {
        // Arrange & Act
        String mensagem = "Recurso não encontrado";
        ResourceNotFoundException exception = new ResourceNotFoundException(mensagem);

        // Assert
        assertNotNull(exception);
        assertEquals(mensagem, exception.getMessage());
    }

    @Test
    void resourceNotFoundException_ConstrutorComMensagemECausa_DeveInicializarCorretamente() {
        // Arrange & Act
        String mensagem = "Recurso não encontrado";
        Throwable causa = new RuntimeException("Causa original");
        ResourceNotFoundException exception = new ResourceNotFoundException(mensagem, causa);

        // Assert
        assertNotNull(exception);
        assertEquals(mensagem, exception.getMessage());
        assertEquals(causa, exception.getCause());
    }

    @Test
    void communicationException_ConstrutorComMensagem_DeveInicializarCorretamente() {
        // Arrange & Act
        String mensagem = "Erro de comunicação";
        CommunicationException exception = new CommunicationException(mensagem);

        // Assert
        assertNotNull(exception);
        assertEquals(mensagem, exception.getMessage());
    }

    @Test
    void communicationException_ConstrutorComMensagemECausa_DeveInicializarCorretamente() {
        // Arrange & Act
        String mensagem = "Erro de comunicação";
        Throwable causa = new RuntimeException("Causa original");
        CommunicationException exception = new CommunicationException(mensagem, causa);

        // Assert
        assertNotNull(exception);
        assertEquals(mensagem, exception.getMessage());
        assertEquals(causa, exception.getCause());
    }
}
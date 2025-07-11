package destaxa.autorizador.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class TransactionProcessingExceptionTest {

    @Test
    void transactionProcessingException_ConstrutorComMensagem_DeveInicializarCorretamente() {
        // Arrange & Act
        String mensagem = "Erro no processamento da transação";
        TransactionProcessingException exception = new TransactionProcessingException(mensagem);

        // Assert
        assertNotNull(exception);
        assertEquals(mensagem, exception.getMessage());
    }

    @Test
    void transactionProcessingException_ConstrutorComMensagemECausa_DeveInicializarCorretamente() {
        // Arrange & Act
        String mensagem = "Erro no processamento da transação";
        Throwable causa = new RuntimeException("Causa original");
        TransactionProcessingException exception = new TransactionProcessingException(mensagem, causa);

        // Assert
        assertNotNull(exception);
        assertEquals(mensagem, exception.getMessage());
        assertEquals(causa, exception.getCause());
    }
}
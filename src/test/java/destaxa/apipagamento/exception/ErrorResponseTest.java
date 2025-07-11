package destaxa.apipagamento.exception;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ErrorResponseTest {

    @Test
    void constructor_DeveInicializarTodosOsCampos() {
        // Arrange
        LocalDateTime timestamp = LocalDateTime.now();
        int status = 400;
        String error = "Bad Request";
        String message = "Erro de validação";
        String path = "/api/v1/pagamentos/authorization";

        // Act
        ErrorResponse errorResponse = new ErrorResponse(timestamp, status, error, message, path);

        // Assert
        assertNotNull(errorResponse);
        assertEquals(timestamp, errorResponse.getTimestamp());
        assertEquals(status, errorResponse.getStatus());
        assertEquals(error, errorResponse.getError());
        assertEquals(message, errorResponse.getMessage());
        assertEquals(path, errorResponse.getPath());
    }

    @Test
    void settersAndGetters_DevemFuncionarCorretamente() {
        // Arrange
        ErrorResponse errorResponse = new ErrorResponse(null, 0, null, null, null);
        LocalDateTime timestamp = LocalDateTime.now();
        int status = 500;
        String error = "Internal Server Error";
        String message = "Erro interno do servidor";
        String path = "/api/v1/pagamentos/authorization";

        // Act
        errorResponse.setTimestamp(timestamp);
        errorResponse.setStatus(status);
        errorResponse.setError(error);
        errorResponse.setMessage(message);
        errorResponse.setPath(path);

        // Assert
        assertEquals(timestamp, errorResponse.getTimestamp());
        assertEquals(status, errorResponse.getStatus());
        assertEquals(error, errorResponse.getError());
        assertEquals(message, errorResponse.getMessage());
        assertEquals(path, errorResponse.getPath());
    }
}
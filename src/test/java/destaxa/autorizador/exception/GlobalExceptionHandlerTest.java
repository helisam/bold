package destaxa.autorizador.exception;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.context.request.WebRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

public class GlobalExceptionHandlerTest {

    @InjectMocks
    private GlobalExceptionHandler exceptionHandler;

    @Mock
    private WebRequest webRequest;

    @Mock
    private MethodArgumentNotValidException methodArgumentNotValidException;

    @Mock
    private BindingResult bindingResult;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        when(webRequest.getDescription(false)).thenReturn("test/uri");
    }

    @Test
    void handleAllExceptions_DeveRetornarStatus500() {
        // Arrange
        Exception exception = new Exception("Erro interno de teste");

        // Act
        ResponseEntity<ErrorResponse> responseEntity = exceptionHandler.handleAllExceptions(exception, webRequest);

        // Assert
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), responseEntity.getBody().getStatus());
        assertEquals("Erro interno do servidor", responseEntity.getBody().getError());
        assertEquals("Erro interno de teste", responseEntity.getBody().getMessage());
        assertEquals("test/uri", responseEntity.getBody().getPath());
    }

    @Test
    void handleValidationExceptions_DeveRetornarStatus400ComErrosDeValidacao() {
        // Arrange
        List<FieldError> fieldErrors = new ArrayList<>();
        fieldErrors.add(new FieldError("authorizationRequest", "cardNumber", "Número do cartão é obrigatório"));
        fieldErrors.add(new FieldError("authorizationRequest", "value", "Valor deve ser positivo"));

        when(methodArgumentNotValidException.getBindingResult()).thenReturn(bindingResult);
        when(bindingResult.getAllErrors()).thenReturn(new ArrayList<>(fieldErrors));

        // Act
        ResponseEntity<Map<String, String>> responseEntity = exceptionHandler.handleValidationExceptions(methodArgumentNotValidException);

        // Assert
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals(2, responseEntity.getBody().size());
        assertTrue(responseEntity.getBody().containsKey("cardNumber"));
        assertTrue(responseEntity.getBody().containsKey("value"));
        assertEquals("Número do cartão é obrigatório", responseEntity.getBody().get("cardNumber"));
        assertEquals("Valor deve ser positivo", responseEntity.getBody().get("value"));
    }

    @Test
    void handleResourceNotFoundException_DeveRetornarStatus404() {
        // Arrange
        ResourceNotFoundException exception = new ResourceNotFoundException("Recurso não encontrado");

        // Act
        ResponseEntity<ErrorResponse> responseEntity = exceptionHandler.handleResourceNotFoundException(exception, webRequest);

        // Assert
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals(HttpStatus.NOT_FOUND.value(), responseEntity.getBody().getStatus());
        assertEquals("Recurso não encontrado", responseEntity.getBody().getError());
        assertEquals("Recurso não encontrado", responseEntity.getBody().getMessage());
    }

    @Test
    void handleBusinessException_DeveRetornarStatus400() {
        // Arrange
        BusinessException exception = new BusinessException("Erro de negócio");

        // Act
        ResponseEntity<ErrorResponse> responseEntity = exceptionHandler.handleBusinessException(exception, webRequest);

        // Assert
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals(HttpStatus.BAD_REQUEST.value(), responseEntity.getBody().getStatus());
        assertEquals("Erro de negócio", responseEntity.getBody().getError());
        assertEquals("Erro de negócio", responseEntity.getBody().getMessage());
    }

    @Test
    void handleTransactionProcessingException_DeveRetornarStatus422() {
        // Arrange
        TransactionProcessingException exception = new TransactionProcessingException("Erro no processamento da transação");

        // Act
        ResponseEntity<ErrorResponse> responseEntity = exceptionHandler.handleTransactionProcessingException(exception, webRequest);

        // Assert
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY.value(), responseEntity.getBody().getStatus());
        assertEquals("Erro no processamento da transação", responseEntity.getBody().getError());
        assertEquals("Erro no processamento da transação", responseEntity.getBody().getMessage());
    }
}
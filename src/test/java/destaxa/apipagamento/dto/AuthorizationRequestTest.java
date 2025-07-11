package destaxa.apipagamento.dto;

import org.junit.jupiter.api.Test;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class AuthorizationRequestTest {

    private final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    private final Validator validator = factory.getValidator();

    @Test
    void testValidRequest() {
        // Arrange
        AuthorizationRequest request = new AuthorizationRequest();
        request.setExternalId("123456");
        request.setValue(100.00);
        request.setCardNumber("4111111111111111");
        request.setInstallments(1);
        request.setCvv("123");
        request.setExpMonth(12);
        request.setExpYear(2025);
        request.setHolderName("John Doe");
        request.setCodigoEstabelecimento("12345678");

        // Act
        Set<ConstraintViolation<AuthorizationRequest>> violations = validator.validate(request);

        // Assert
        assertTrue(violations.isEmpty());
    }

    @Test
    void testNullExternalId() {
        // Arrange
        AuthorizationRequest request = new AuthorizationRequest();
        request.setExternalId(null);
        request.setValue(100.00);
        request.setCardNumber("4111111111111111");
        request.setInstallments(1);
        request.setCvv("123");
        request.setExpMonth(12);
        request.setExpYear(2025);
        request.setHolderName("John Doe");
        request.setCodigoEstabelecimento("12345678");

        // Act
        Set<ConstraintViolation<AuthorizationRequest>> violations = validator.validate(request);

        // Assert
        assertFalse(violations.isEmpty());
        assertEquals(1, violations.size());
        assertEquals("externalId não pode ser nulo", violations.iterator().next().getMessage());
    }

    @Test
    void testNegativeValue() {
        // Arrange
        AuthorizationRequest request = new AuthorizationRequest();
        request.setExternalId("123456");
        request.setValue(-100.00);
        request.setCardNumber("4111111111111111");
        request.setInstallments(1);
        request.setCvv("123");
        request.setExpMonth(12);
        request.setExpYear(2025);
        request.setHolderName("John Doe");
        request.setCodigoEstabelecimento("12345678");

        // Act
        Set<ConstraintViolation<AuthorizationRequest>> violations = validator.validate(request);

        // Assert
        assertFalse(violations.isEmpty());
        assertEquals(1, violations.size());
        assertEquals("value deve ser maior que 0", violations.iterator().next().getMessage());
    }

    @Test
    void testNullCardNumber() {
        // Arrange
        AuthorizationRequest request = new AuthorizationRequest();
        request.setExternalId("123456");
        request.setValue(100.00);
        request.setCardNumber(null);
        request.setInstallments(1);
        request.setCvv("123");
        request.setExpMonth(12);
        request.setExpYear(2025);
        request.setHolderName("John Doe");
        request.setCodigoEstabelecimento("12345678");

        // Act
        Set<ConstraintViolation<AuthorizationRequest>> violations = validator.validate(request);

        // Assert
        assertFalse(violations.isEmpty());
        assertEquals(1, violations.size());
        assertEquals("cardNumber não pode ser nulo", violations.iterator().next().getMessage());
    }

    @Test
    void testShortCardNumber() {
        // Arrange
        AuthorizationRequest request = new AuthorizationRequest();
        request.setExternalId("123456");
        request.setValue(100.00);
        request.setCardNumber("41111");
        request.setInstallments(1);
        request.setCvv("123");
        request.setExpMonth(12);
        request.setExpYear(2025);
        request.setHolderName("John Doe");
        request.setCodigoEstabelecimento("12345678");

        // Act
        Set<ConstraintViolation<AuthorizationRequest>> violations = validator.validate(request);

        // Assert
        assertFalse(violations.isEmpty());
        assertEquals(1, violations.size());
        assertEquals("cardNumber deve ter entre 13 e 19 caracteres", violations.iterator().next().getMessage());
    }

    @Test
    void testZeroInstallments() {
        // Arrange
        AuthorizationRequest request = new AuthorizationRequest();
        request.setExternalId("123456");
        request.setValue(100.00);
        request.setCardNumber("4111111111111111");
        request.setInstallments(0);
        request.setCvv("123");
        request.setExpMonth(12);
        request.setExpYear(2025);
        request.setHolderName("John Doe");
        request.setCodigoEstabelecimento("12345678");

        // Act
        Set<ConstraintViolation<AuthorizationRequest>> violations = validator.validate(request);

        // Assert
        assertFalse(violations.isEmpty());
        assertEquals(1, violations.size());
        assertEquals("installments deve ser entre 1 e 99", violations.iterator().next().getMessage());
    }

    @Test
    void testTooManyInstallments() {
        // Arrange
        AuthorizationRequest request = new AuthorizationRequest();
        request.setExternalId("123456");
        request.setValue(100.00);
        request.setCardNumber("4111111111111111");
        request.setInstallments(100);
        request.setCvv("123");
        request.setExpMonth(12);
        request.setExpYear(2025);
        request.setHolderName("John Doe");
        request.setCodigoEstabelecimento("12345678");

        // Act
        Set<ConstraintViolation<AuthorizationRequest>> violations = validator.validate(request);

        // Assert
        assertFalse(violations.isEmpty());
        assertEquals(1, violations.size());
        assertEquals("installments deve ser entre 1 e 99", violations.iterator().next().getMessage());
    }

    @Test
    void testNullCvv() {
        // Arrange
        AuthorizationRequest request = new AuthorizationRequest();
        request.setExternalId("123456");
        request.setValue(100.00);
        request.setCardNumber("4111111111111111");
        request.setInstallments(1);
        request.setCvv(null);
        request.setExpMonth(12);
        request.setExpYear(2025);
        request.setHolderName("John Doe");
        request.setCodigoEstabelecimento("12345678");

        // Act
        Set<ConstraintViolation<AuthorizationRequest>> violations = validator.validate(request);

        // Assert
        assertFalse(violations.isEmpty());
        assertEquals(1, violations.size());
        assertEquals("cvv não pode ser nulo", violations.iterator().next().getMessage());
    }

    @Test
    void testShortCvv() {
        // Arrange
        AuthorizationRequest request = new AuthorizationRequest();
        request.setExternalId("123456");
        request.setValue(100.00);
        request.setCardNumber("4111111111111111");
        request.setInstallments(1);
        request.setCvv("1");
        request.setExpMonth(12);
        request.setExpYear(2025);
        request.setHolderName("John Doe");
        request.setCodigoEstabelecimento("12345678");

        // Act
        Set<ConstraintViolation<AuthorizationRequest>> violations = validator.validate(request);

        // Assert
        assertFalse(violations.isEmpty());
        assertEquals(1, violations.size());
        assertEquals("cvv deve ter entre 3 e 4 caracteres", violations.iterator().next().getMessage());
    }

    @Test
    void testZeroExpMonth() {
        // Arrange
        AuthorizationRequest request = new AuthorizationRequest();
        request.setExternalId("123456");
        request.setValue(100.00);
        request.setCardNumber("4111111111111111");
        request.setInstallments(1);
        request.setCvv("123");
        request.setExpMonth(0);
        request.setExpYear(2025);
        request.setHolderName("John Doe");
        request.setCodigoEstabelecimento("12345678");

        // Act
        Set<ConstraintViolation<AuthorizationRequest>> violations = validator.validate(request);

        // Assert
        assertFalse(violations.isEmpty());
        assertEquals(1, violations.size());
        assertEquals("expMonth deve ser entre 1 e 12", violations.iterator().next().getMessage());
    }

    @Test
    void testInvalidExpMonth() {
        // Arrange
        AuthorizationRequest request = new AuthorizationRequest();
        request.setExternalId("123456");
        request.setValue(100.00);
        request.setCardNumber("4111111111111111");
        request.setInstallments(1);
        request.setCvv("123");
        request.setExpMonth(13);
        request.setExpYear(2025);
        request.setHolderName("John Doe");
        request.setCodigoEstabelecimento("12345678");

        // Act
        Set<ConstraintViolation<AuthorizationRequest>> violations = validator.validate(request);

        // Assert
        assertFalse(violations.isEmpty());
        assertEquals(1, violations.size());
        assertEquals("expMonth deve ser entre 1 e 12", violations.iterator().next().getMessage());
    }

    @Test
    void testNegativeExpYear() {
        // Arrange
        AuthorizationRequest request = new AuthorizationRequest();
        request.setExternalId("123456");
        request.setValue(100.00);
        request.setCardNumber("4111111111111111");
        request.setInstallments(1);
        request.setCvv("123");
        request.setExpMonth(12);
        request.setExpYear(-2025);
        request.setHolderName("John Doe");
        request.setCodigoEstabelecimento("12345678");

        // Act
        Set<ConstraintViolation<AuthorizationRequest>> violations = validator.validate(request);

        // Assert
        assertFalse(violations.isEmpty());
        assertEquals(1, violations.size());
        assertEquals("expYear deve ser maior que 0", violations.iterator().next().getMessage());
    }

    @Test
    void testNullHolderName() {
        // Arrange
        AuthorizationRequest request = new AuthorizationRequest();
        request.setExternalId("123456");
        request.setValue(100.00);
        request.setCardNumber("4111111111111111");
        request.setInstallments(1);
        request.setCvv("123");
        request.setExpMonth(12);
        request.setExpYear(2025);
        request.setHolderName(null);
        request.setCodigoEstabelecimento("12345678");

        // Act
        Set<ConstraintViolation<AuthorizationRequest>> violations = validator.validate(request);

        // Assert
        assertFalse(violations.isEmpty());
        assertEquals(1, violations.size());
        assertEquals("holderName não pode ser nulo", violations.iterator().next().getMessage());
    }

    @Test
    void testGettersAndSetters() {
        // Arrange
        AuthorizationRequest request = new AuthorizationRequest();
        
        // Act
        request.setExternalId("123456");
        request.setValue(100.00);
        request.setCardNumber("4111111111111111");
        request.setInstallments(1);
        request.setCvv("123");
        request.setExpMonth(12);
        request.setExpYear(2025);
        request.setHolderName("John Doe");
        request.setCodigoEstabelecimento("12345678");
        
        // Assert
        assertEquals("123456", request.getExternalId());
        assertEquals(100.00, request.getValue());
        assertEquals("4111111111111111", request.getCardNumber());
        assertEquals(1, request.getInstallments());
        assertEquals("123", request.getCvv());
        assertEquals(12, request.getExpMonth());
        assertEquals(2025, request.getExpYear());
        assertEquals("John Doe", request.getHolderName());
        assertEquals("12345678", request.getCodigoEstabelecimento());
    }
}
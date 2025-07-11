package destaxa.apipagamento.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AuthorizationResponseTest {

    @Test
    void testGettersAndSetters() {
        // Arrange
        AuthorizationResponse response = new AuthorizationResponse();
        
        // Act
        response.setPaymentId("123456");
        response.setValue(100.00);
        response.setResponseCode("000");
        response.setAuthorizationCode("123456");
        response.setTransactionDate("20230712");
        response.setTransactionHour("123456");
        
        // Assert
        assertEquals("123456", response.getPaymentId());
        assertEquals(100.00, response.getValue());
        assertEquals("000", response.getResponseCode());
        assertEquals("123456", response.getAuthorizationCode());
        assertEquals("20230712", response.getTransactionDate());
        assertEquals("123456", response.getTransactionHour());
    }
    
    @Test
    void testToString() {
        // Arrange
        AuthorizationResponse response = new AuthorizationResponse();
        response.setPaymentId("123456");
        response.setValue(100.00);
        response.setResponseCode("000");
        response.setAuthorizationCode("123456");
        response.setTransactionDate("20230712");
        response.setTransactionHour("123456");
        
        // Act
        String toString = response.toString();
        
        // Assert
        // Verificar se o toString contém os campos principais
        assertTrue(toString.contains("paymentId=123456"));
        assertTrue(toString.contains("value=100.0"));
        assertTrue(toString.contains("responseCode=000"));
        assertTrue(toString.contains("authorizationCode=123456"));
        assertTrue(toString.contains("transactionDate=20230712"));
        assertTrue(toString.contains("transactionHour=123456"));
    }
    
    @Test
    void testEqualsAndHashCode() {
        // Arrange
        AuthorizationResponse response1 = new AuthorizationResponse();
        response1.setPaymentId("123456");
        response1.setValue(100.00);
        response1.setResponseCode("000");
        response1.setAuthorizationCode("123456");
        response1.setTransactionDate("20230712");
        response1.setTransactionHour("123456");
        
        AuthorizationResponse response2 = new AuthorizationResponse();
        response2.setPaymentId("123456");
        response2.setValue(100.00);
        response2.setResponseCode("000");
        response2.setAuthorizationCode("123456");
        response2.setTransactionDate("20230712");
        response2.setTransactionHour("123456");
        
        // Assert
        assertEquals(response1, response2);
        assertEquals(response1.hashCode(), response2.hashCode());
    }
}
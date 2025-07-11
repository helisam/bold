package destaxa.autorizador.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TransacaoResponseDTOTest {

    @Test
    void testGettersAndSetters() {
        // Arrange
        TransacaoResponseDTO response = new TransacaoResponseDTO();
        
        // Act
        response.setCodigoMensagem("0210");
        response.setValor(100.00);
        response.setDataTransacao("20230712");
        response.setHoraTransacao("123456");
        response.setNsu("123456");
        response.setCodigoAutorizacao("123456");
        response.setCodigoResposta("000");
        response.setCodigoEstabelecimento("12345678");
        response.setNsuHost("123456");
        
        // Assert
        assertEquals("0210", response.getCodigoMensagem());
        assertEquals(100.00, response.getValor());
        assertEquals("20230712", response.getDataTransacao());
        assertEquals("123456", response.getHoraTransacao());
        assertEquals("123456", response.getNsu());
        assertEquals("123456", response.getCodigoAutorizacao());
        assertEquals("000", response.getCodigoResposta());
        assertEquals("12345678", response.getCodigoEstabelecimento());
        assertEquals("123456", response.getNsuHost());
    }
    
    @Test
    void testEqualsAndHashCode() {
        // Arrange
        TransacaoResponseDTO response1 = new TransacaoResponseDTO();
        response1.setCodigoMensagem("0210");
        response1.setValor(100.00);
        response1.setDataTransacao("20230712");
        response1.setHoraTransacao("123456");
        response1.setNsu("123456");
        response1.setCodigoAutorizacao("123456");
        response1.setCodigoResposta("000");
        response1.setCodigoEstabelecimento("12345678");
        response1.setNsuHost("123456");
        
        TransacaoResponseDTO response2 = new TransacaoResponseDTO();
        response2.setCodigoMensagem("0210");
        response2.setValor(100.00);
        response2.setDataTransacao("20230712");
        response2.setHoraTransacao("123456");
        response2.setNsu("123456");
        response2.setCodigoAutorizacao("123456");
        response2.setCodigoResposta("000");
        response2.setCodigoEstabelecimento("12345678");
        response2.setNsuHost("123456");
        
        // Assert
        assertEquals(response1, response2);
        assertEquals(response1.hashCode(), response2.hashCode());
    }
    
    @Test
    void testToString() {
        // Arrange
        TransacaoResponseDTO response = new TransacaoResponseDTO();
        response.setCodigoMensagem("0210");
        response.setValor(100.00);
        response.setCodigoResposta("000");
        response.setCodigoAutorizacao("123456");
        
        // Act
        String toString = response.toString();
        
        // Assert
        // Verificar se o toString contém os campos principais
        assertTrue(toString.contains("codigoMensagem=0210"));
        assertTrue(toString.contains("valor=100.0"));
        assertTrue(toString.contains("codigoResposta=000"));
        assertTrue(toString.contains("codigoAutorizacao=123456"));
    }
}
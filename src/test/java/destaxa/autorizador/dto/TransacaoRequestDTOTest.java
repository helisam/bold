package destaxa.autorizador.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TransacaoRequestDTOTest {

    @Test
    void testGettersAndSetters() {
        // Arrange
        TransacaoRequestDTO request = new TransacaoRequestDTO();
        
        // Act
        request.setCodigoMensagem("0200");
        request.setNumeroCartao("4111111111111111");
        request.setCodigoProcessamento("003000");
        request.setValor(100.00);
        request.setDataTransacao("20230712");
        request.setHoraTransacao("123456");
        request.setNsu("123456");
        request.setCodigoEstabelecimento("12345678");
        request.setNsuHost("123456");
        request.setModoEntrada("012");
        request.setCodigoSeguranca("123");
        request.setParcelas("1");
        
        // Assert
        assertEquals("0200", request.getCodigoMensagem());
        assertEquals("4111111111111111", request.getNumeroCartao());
        assertEquals("003000", request.getCodigoProcessamento());
        assertEquals(100.00, request.getValor());
        assertEquals("20230712", request.getDataTransacao());
        assertEquals("123456", request.getHoraTransacao());
        assertEquals("123456", request.getNsu());
        assertEquals("12345678", request.getCodigoEstabelecimento());
        assertEquals("123456", request.getNsuHost());
        assertEquals("012", request.getModoEntrada());
        assertEquals("123", request.getCodigoSeguranca());
        assertEquals("1", request.getParcelas());
    }
    
    @Test
    void testEqualsAndHashCode() {
        // Arrange
        TransacaoRequestDTO request1 = new TransacaoRequestDTO();
        request1.setCodigoMensagem("0200");
        request1.setNumeroCartao("4111111111111111");
        request1.setCodigoProcessamento("003000");
        request1.setValor(100.00);
        request1.setDataTransacao("20230712");
        request1.setHoraTransacao("123456");
        request1.setNsu("123456");
        request1.setCodigoEstabelecimento("12345678");
        request1.setNsuHost("123456");
        request1.setModoEntrada("012");
        request1.setCodigoSeguranca("123");
        request1.setParcelas("1");
        
        TransacaoRequestDTO request2 = new TransacaoRequestDTO();
        request2.setCodigoMensagem("0200");
        request2.setNumeroCartao("4111111111111111");
        request2.setCodigoProcessamento("003000");
        request2.setValor(100.00);
        request2.setDataTransacao("20230712");
        request2.setHoraTransacao("123456");
        request2.setNsu("123456");
        request2.setCodigoEstabelecimento("12345678");
        request2.setNsuHost("123456");
        request2.setModoEntrada("012");
        request2.setCodigoSeguranca("123");
        request2.setParcelas("1");
        
        // Assert
        assertEquals(request1, request2);
        assertEquals(request1.hashCode(), request2.hashCode());
    }
    
    @Test
    void testToString() {
        // Arrange
        TransacaoRequestDTO request = new TransacaoRequestDTO();
        request.setCodigoMensagem("0200");
        request.setNumeroCartao("4111111111111111");
        request.setValor(100.00);
        
        // Act
        String toString = request.toString();
        
        // Assert
        // Verificar se o toString contém os campos principais
        assertTrue(toString.contains("codigoMensagem=0200"));
        assertTrue(toString.contains("numeroCartao=4111111111111111"));
        assertTrue(toString.contains("valor=100.0"));
    }
}
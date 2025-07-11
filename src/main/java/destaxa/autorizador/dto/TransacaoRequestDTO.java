package destaxa.autorizador.dto;

import lombok.Data;

@Data
public class TransacaoRequestDTO {
    private String codigoMensagem;
    private String numeroCartao;
    private String codigoProcessamento;
    private double valor;
    private String dataTransacao;
    private String horaTransacao;
    private String nsu;
    private String codigoEstabelecimento;
    private String nsuHost;
    private String modoEntrada;
    private String codigoSeguranca;
    private String parcelas;
}
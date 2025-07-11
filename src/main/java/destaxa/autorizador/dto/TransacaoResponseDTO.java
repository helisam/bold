package destaxa.autorizador.dto;

import lombok.Data;

@Data
public class TransacaoResponseDTO {
    private String codigoMensagem;
    private double valor;
    private String dataTransacao;
    private String horaTransacao;
    private String nsu;
    private String codigoAutorizacao;
    private String codigoResposta;
    private String codigoEstabelecimento;
    private String nsuHost;
}
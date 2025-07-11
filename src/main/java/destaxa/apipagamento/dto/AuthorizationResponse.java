package destaxa.apipagamento.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class AuthorizationResponse {
    private String paymentId;
    private double value;
    private String responseCode;
    private String authorizationCode;
    private String transactionDate;
    private String transactionHour;
}
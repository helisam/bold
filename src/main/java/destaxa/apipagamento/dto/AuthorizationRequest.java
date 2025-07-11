package destaxa.apipagamento.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class AuthorizationRequest {
    @NotBlank
    private String externalId;
    
    @Positive
    private double value;
    
    @NotBlank
    @Size(min = 16, max = 19)
    private String cardNumber;
    
    @Min(1)
    @Max(99)
    private int installments;
    
    @NotBlank
    @Size(min = 3, max = 4)
    private String cvv;
    
    @Min(1)
    @Max(12)
    private int expMonth;
    
    @Min(0)
    private int expYear;
    
    @NotBlank
    private String holderName;
    
    private String codigoEstabelecimento;
}
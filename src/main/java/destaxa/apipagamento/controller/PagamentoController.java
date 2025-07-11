package destaxa.apipagamento.controller;

import destaxa.apipagamento.dto.AuthorizationRequest;
import destaxa.apipagamento.dto.AuthorizationResponse;
import destaxa.apipagamento.service.PagamentoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/pagamentos")
@Tag(name = "Pagamentos", description = "API para processamento de pagamentos")
public class PagamentoController {

    private static final Logger logger = LoggerFactory.getLogger(PagamentoController.class);
    private final PagamentoService pagamentoService;

    public PagamentoController(PagamentoService pagamentoService) {
        this.pagamentoService = pagamentoService;
        logger.info("PagamentoController inicializado");
    }

    @Operation(summary = "Autorizar pagamento", description = "Processa uma solicitação de pagamento e retorna a resposta da autorização")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Pagamento processado com sucesso",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = AuthorizationResponse.class))),
        @ApiResponse(responseCode = "400", description = "Requisição inválida", content = @Content),
        @ApiResponse(responseCode = "500", description = "Erro interno do servidor", content = @Content)
    })
    @PostMapping("/authorization")
    public ResponseEntity<AuthorizationResponse> autorizarPagamento(
            @Parameter(description = "Identificador do estabelecimento", required = true)
            @RequestHeader("x-identifier") String identifier,
            @Parameter(description = "Dados da requisição de autorização", required = true)
            @Valid @RequestBody AuthorizationRequest request) {
        
        logger.info("Recebida requisição de autorização para o estabelecimento: {}", identifier);
        logger.debug("Dados da requisição: {}", request);
        
        request.setCodigoEstabelecimento(identifier);
        AuthorizationResponse response = pagamentoService.processarAutorizacao(request);
        
        return ResponseEntity.ok(response);
    }
    
    @Operation(summary = "Obter status do serviço", description = "Retorna informações sobre o status do serviço de pagamento")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Informações obtidas com sucesso")
    })
    @GetMapping("/status")
    public ResponseEntity<Map<String, Object>> getStatus() {
        Map<String, Object> status = new HashMap<>();
        status.put("service", "api-pagamento");
        status.put("status", "online");
        
        logger.info("Status do serviço solicitado");
        return ResponseEntity.ok(status);
    }

}
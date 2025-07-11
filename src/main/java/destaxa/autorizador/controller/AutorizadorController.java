package destaxa.autorizador.controller;

import destaxa.autorizador.service.AutorizadorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/autorizador")
@Tag(name = "Autorizador", description = "API para informações sobre o serviço de autorização")
public class AutorizadorController {

    private final AutorizadorService autorizadorService;
    
    @Value("${autorizador.socket.port}")
    private int socketPort;
    
    @Value("${autorizador.socket.pool-size}")
    private int poolSize;

    public AutorizadorController(AutorizadorService autorizadorService) {
        this.autorizadorService = autorizadorService;
    }

    @Operation(summary = "Obter status do serviço", description = "Retorna informações sobre o status do serviço de autorização")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Informações obtidas com sucesso")
    })
    @GetMapping("/status")
    public ResponseEntity<Map<String, Object>> getStatus() {
        Map<String, Object> status = new HashMap<>();
        status.put("service", "autorizador");
        status.put("status", "online");
        status.put("socketPort", socketPort);
        status.put("poolSize", poolSize);
        
        return ResponseEntity.ok(status);
    }
}
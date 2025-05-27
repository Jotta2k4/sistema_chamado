package com.example.sistema_chamado.controllers;

import com.example.sistema_chamado.dtos.callhistorydto.CallHistoryDTO;
import com.example.sistema_chamado.dtos.callhistorydto.CallHistoryResponseDTO;
import com.example.sistema_chamado.services.CallHistoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("logs")
public class CallHistoryController {
    private final CallHistoryService callHistoryService;

    public CallHistoryController(CallHistoryService callHistoryService) {
        this.callHistoryService = callHistoryService;
    }

    @PostMapping("{calledId}")
    @Operation(summary = "Criando Histórico", description = "Criando um histórico de uma chamada pelo id relacionado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Histórico chamado criado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Chamado não encontrado")
    })
    public ResponseEntity<CallHistoryDTO> createLog (@RequestBody CallHistoryResponseDTO data, @PathVariable Integer calledId) {
        CallHistoryDTO newCallHistory = this.callHistoryService.createHistoricCall(data, calledId);
        return ResponseEntity.status(HttpStatus.OK).body(newCallHistory);
    }
}

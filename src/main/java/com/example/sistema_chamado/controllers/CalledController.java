package com.example.sistema_chamado.controllers;

import com.example.sistema_chamado.dtos.calleddto.CalledRequestDTO;
import com.example.sistema_chamado.dtos.calleddto.CalledResponseDTO;
import com.example.sistema_chamado.services.CalledService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("called")
public class CalledController {
    private final CalledService calledService;

    public CalledController(CalledService calledService) {
        this.calledService = calledService;
    }

    @PostMapping
    @Operation(summary = "Criar Um Chamado", description = "Criando um chamado a um cliente e relacionando a um técnico")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Chamado criado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Cliente relacionado ao chamado não foi encontrado"),
            @ApiResponse(responseCode = "400", description = "Não possui técnico disponível no momento")
    })
    public ResponseEntity<CalledResponseDTO> createCalled (@RequestBody CalledRequestDTO data) {
        CalledResponseDTO called = this.calledService.createCalled(data);
        return ResponseEntity.status(HttpStatus.CREATED).body(called);
    }

}

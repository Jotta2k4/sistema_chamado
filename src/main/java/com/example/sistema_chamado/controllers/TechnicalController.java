package com.example.sistema_chamado.controllers;

import com.example.sistema_chamado.dtos.technicaldto.TechnicalRequestDTO;
import com.example.sistema_chamado.dtos.technicaldto.TechnicalResponseDTO;
import com.example.sistema_chamado.models.Technical;
import com.example.sistema_chamado.services.TechnicalService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("technical")
public class TechnicalController {
    private final TechnicalService technicalService;

    public TechnicalController(TechnicalService technicalService) {
        this.technicalService = technicalService;
    }

    @GetMapping("{name}")
    @Operation(summary = "Buscar Técnico Pelo Nome", description = "Retorna uma lista de técnicos com determinado nome")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Técnico encontrado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Técnico não encontrado")
    })
    public ResponseEntity<List<Technical>> findByName (
            @Parameter(description = "Nome a ser buscado", example = "name?name=wilson")
            @RequestParam String name) {
        List<Technical> technicals = this.technicalService.findByNameAll(name);
        return ResponseEntity.status(HttpStatus.OK).body(technicals);
    }

    @PostMapping
    @Operation(summary = "Criar Um Novo Técnico", description = "Criar um novo técnico ao sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Técnico criado com sucesso"),
            @ApiResponse(responseCode = "409", description = "Ocorreu algum conflito")
    })
    public ResponseEntity<TechnicalResponseDTO> createTechnical (@RequestBody TechnicalRequestDTO data) {
       TechnicalResponseDTO technicalResponseDTO = this.technicalService.createTechnical(data);
        return ResponseEntity.status(HttpStatus.CREATED).body(technicalResponseDTO);
    }
}

package com.example.sistema_chamado.controllers;

import com.example.sistema_chamado.dtos.technicaldto.TechnicalRequestDTO;
import com.example.sistema_chamado.dtos.technicaldto.TechnicalResponseDTO;
import com.example.sistema_chamado.models.Technical;
import com.example.sistema_chamado.services.TechnicalService;
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
    public ResponseEntity<List<Technical>> findByName (@RequestParam String name) {
        List<Technical> technicals = this.technicalService.findByNameAll(name);
        return ResponseEntity.status(HttpStatus.OK).body(technicals);
    }

    @PostMapping
    public ResponseEntity<TechnicalResponseDTO> createTechnical (@RequestBody TechnicalRequestDTO data) {
       TechnicalResponseDTO technicalResponseDTO = this.technicalService.createTechnical(data);
        return ResponseEntity.status(HttpStatus.CREATED).body(technicalResponseDTO);
    }
}

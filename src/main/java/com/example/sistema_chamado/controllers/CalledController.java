package com.example.sistema_chamado.controllers;

import com.example.sistema_chamado.dtos.calleddto.CalledRequestDTO;
import com.example.sistema_chamado.dtos.calleddto.CalledResponseDTO;
import com.example.sistema_chamado.services.CalledService;
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
    public ResponseEntity<CalledResponseDTO> createCalled (@RequestBody CalledRequestDTO data) {
        CalledResponseDTO called = this.calledService.createCalled(data);
        return ResponseEntity.status(HttpStatus.CREATED).body(called);
    }

}

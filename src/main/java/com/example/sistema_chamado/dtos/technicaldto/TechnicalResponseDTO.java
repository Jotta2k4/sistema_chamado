package com.example.sistema_chamado.dtos.technicaldto;

import jakarta.validation.constraints.NotBlank;

public record TechnicalResponseDTO (
        @NotBlank(message = "Insira um ID válido!")
        Integer id,
        @NotBlank(message = "Insira um nome válido!")
        String name,
        @NotBlank(message = "Insira um email válido!")
        String email
){
}

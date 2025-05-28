package com.example.sistema_chamado.dtos.technicaldto;

import jakarta.validation.constraints.NotBlank;

public record TechnicalRequestDTO(
        @NotBlank(message = "Insira um nome válido!")
        String name,
        @NotBlank(message = "Insira um email válido!")
        String email,
        @NotBlank(message = "Insira uma senha válida!")
        String password
) {
}

package com.example.sistema_chamado.dtos.customerdto;

import jakarta.validation.constraints.NotBlank;

public record CustomerResponseDTO(
        @NotBlank(message = "Insira um ID válido!")
        Integer id,
        @NotBlank(message = "Insira um nome válido!")
        String name,
        @NotBlank(message = "Insira um email válido!")
        String email,
        @NotBlank(message = "Insira um telefone válido!")
        String phone) {
}

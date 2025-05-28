package com.example.sistema_chamado.dtos.customerdto;

import jakarta.validation.constraints.NotBlank;

public record CustomerRequestDTO(
        @NotBlank(message = "Um nome válido é obrigatório")
        String name,
        @NotBlank(message = "Um email válido é obrigatório.")
        String email,
        @NotBlank(message = "Uma senha válida é obrigatório.")
        String password,
        @NotBlank(message = "Um telefone válido é obrigatório.")
        String phone) {
}

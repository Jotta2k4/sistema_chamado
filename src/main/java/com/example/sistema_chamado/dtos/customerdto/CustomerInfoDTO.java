package com.example.sistema_chamado.dtos.customerdto;

import jakarta.validation.constraints.NotBlank;

public record CustomerInfoDTO(
        @NotBlank(message = "O seu nome está vázio")
        String name,
        @NotBlank(message = "Email está vázio")
        String email,
        @NotBlank(message = "Telefone está vázio")
        String phone) {
}

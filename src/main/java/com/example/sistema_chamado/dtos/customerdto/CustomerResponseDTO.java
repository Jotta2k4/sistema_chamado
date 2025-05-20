package com.example.sistema_chamado.dtos.customerdto;

import jakarta.validation.constraints.NotBlank;

public record CustomerResponseDTO(
        Integer id,

        String name,

        String email,

        String phone) {
}

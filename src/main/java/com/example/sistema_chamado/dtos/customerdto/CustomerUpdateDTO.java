package com.example.sistema_chamado.dtos.customerdto;

import jakarta.validation.constraints.NotBlank;

public record CustomerUpdateDTO(
        @NotBlank(message = "Insira um nome valido!")
        String name,
        String email,

        String phone,
        String password) {
}

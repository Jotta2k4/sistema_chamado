package com.example.sistema_chamado.dtos.customerdto;

import jakarta.validation.constraints.NotBlank;

public record CustomerUpdateDTO(
        @NotBlank(message = "Insira um nome valido!")
        String name,
        @NotBlank(message = "Insira um email valido!")
        String email,
        @NotBlank(message = "Insira um telefone valido!")
        String phone,
        @NotBlank(message = "Insira uma senha valida!")
        String password) {
}

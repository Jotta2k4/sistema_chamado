package com.example.sistema_chamado.dtos.callhistorydto;

import com.example.sistema_chamado.enums.Status;
import jakarta.validation.constraints.NotBlank;

public record CallHistoryResponseDTO(
        @NotBlank(message = "Insira um comentário válido!")
        String comment,
        @NotBlank(message = "Insira um status válido!")
        Status status
) {
}

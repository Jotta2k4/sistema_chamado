package com.example.sistema_chamado.dtos.callhistorydto;

import com.example.sistema_chamado.enums.Status;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDateTime;

public record CallHistoryDTO(
        @NotBlank(message = "Insira um ID válido!")
        Integer id,
        @NotBlank(message = "Insira um comentário válido!")
        String comment,
        @NotBlank(message = "Insira um status válido!")
        Status status,
        @NotBlank(message = "Insira data e hora válidos!")
        LocalDateTime dateTime,
        @NotBlank(message = "Insira um nome de técnico válido!")
        String technicalName
) {}

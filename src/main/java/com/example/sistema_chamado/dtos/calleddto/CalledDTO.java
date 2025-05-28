package com.example.sistema_chamado.dtos.calleddto;

import com.example.sistema_chamado.dtos.callhistorydto.CallHistoryDTO;
import com.example.sistema_chamado.enums.Category;
import com.example.sistema_chamado.enums.Priority;
import com.example.sistema_chamado.enums.Status;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDateTime;
import java.util.List;

public record CalledDTO(
        @NotBlank(message = "Insira um ID válido!")
        Integer id,
        @NotBlank(message = "Insira um título válido!")
        String title,
        @NotBlank(message = "Insira uma descrição válida!")
        String description,
        @NotBlank(message = "Insira uma prioridade válida!")
        Priority priority,
        @NotBlank(message = "Insira uma categoria válida!")
        Category category,
        @NotBlank(message = "Insira um status válido!")
        Status status,
        @NotBlank(message = "Insira uma data de serviço válida!")
        LocalDateTime dateService,
        @NotBlank(message = "Insira um nome de técnico válido!")
        String technicalName,
        @NotBlank(message = "Insira um cliente válido!")
        Integer customer,
        @NotBlank(message = "Insira um histórico válido!")
        List<CallHistoryDTO> histories
) {}

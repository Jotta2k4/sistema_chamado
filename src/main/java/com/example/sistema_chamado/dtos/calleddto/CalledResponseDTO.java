package com.example.sistema_chamado.dtos.calleddto;

import com.example.sistema_chamado.dtos.customerdto.CustomerInfoDTO;
import com.example.sistema_chamado.enums.Category;
import com.example.sistema_chamado.enums.Priority;
import com.example.sistema_chamado.enums.Status;

import java.time.LocalDateTime;

public record CalledResponseDTO(
        Integer id,
        String title,
        String description,
        Priority priority,
        Category category,
        Status status,
        LocalDateTime dateService,
        CustomerInfoDTO customer,
        String technicalName

) { }

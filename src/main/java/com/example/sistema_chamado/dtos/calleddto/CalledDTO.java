package com.example.sistema_chamado.dtos.calleddto;

import com.example.sistema_chamado.dtos.callhistorydto.CallHistoryDTO;
import com.example.sistema_chamado.enums.Category;
import com.example.sistema_chamado.enums.Priority;
import com.example.sistema_chamado.enums.Status;

import java.time.LocalDateTime;
import java.util.List;

public record CalledDTO(

        Integer id,
        String title,
        String description,
        Priority priority,
        Category category,
        Status status,
        LocalDateTime dateService,
        String technicalName,
        Integer customer,
        List<CallHistoryDTO> histories
) {}

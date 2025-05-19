package com.example.sistema_chamado.dtos.callhistorydto;

import com.example.sistema_chamado.enums.Status;
import java.time.LocalDateTime;

public record CallHistoryDTO(
        Integer id,
        String comment,
        Status status,
        LocalDateTime dateTime,
        String technicalName
) {}

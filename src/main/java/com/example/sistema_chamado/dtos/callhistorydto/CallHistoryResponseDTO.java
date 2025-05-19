package com.example.sistema_chamado.dtos.callhistorydto;

import com.example.sistema_chamado.enums.Status;

public record CallHistoryResponseDTO(String comment, Status status) {
}

package com.example.sistema_chamado.dtos.calleddto;

import com.example.sistema_chamado.enums.Category;
import com.example.sistema_chamado.enums.Priority;

public record CalledRequestDTO (
        String title,
        String description,
        Priority priority,
        Category category,
        Integer customerId
) {
}

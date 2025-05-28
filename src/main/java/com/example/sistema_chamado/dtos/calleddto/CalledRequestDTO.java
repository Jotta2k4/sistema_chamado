package com.example.sistema_chamado.dtos.calleddto;

import com.example.sistema_chamado.enums.Category;
import com.example.sistema_chamado.enums.Priority;
import jakarta.validation.constraints.NotBlank;

public record CalledRequestDTO (
        @NotBlank(message = "Insira um título válido!")
        String title,
        @NotBlank(message = "Insira uma descrição válida")
        String description,
        @NotBlank(message = "Insira uma prioridade válida")
        Priority priority,
        @NotBlank(message = "Insira uma categoria válida")
        Category category,
        @NotBlank(message = "Insira um ID de cliente válido!")
        Integer customerId
) {
}

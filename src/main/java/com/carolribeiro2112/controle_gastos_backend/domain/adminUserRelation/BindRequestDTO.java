package com.carolribeiro2112.controle_gastos_backend.domain.adminUserRelation;

import jakarta.validation.constraints.NotBlank;

public record BindRequestDTO(
       @NotBlank String adminId,
       @NotBlank String userId
) {
}

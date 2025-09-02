package com.carolribeiro2112.controle_gastos_backend.domain.transaction;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record TransactionDTO(
    @NotBlank String userId,
    String description,
    @NotNull BigDecimal value,
    @NotNull TransactionType type
) {
}

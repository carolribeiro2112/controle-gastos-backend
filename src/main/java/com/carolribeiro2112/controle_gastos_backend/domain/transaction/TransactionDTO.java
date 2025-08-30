package com.carolribeiro2112.controle_gastos_backend.domain.transaction;

import java.math.BigDecimal;

public record TransactionDTO(
    String userId,
    String description,
    BigDecimal value,
    TransactionType type
) {
}

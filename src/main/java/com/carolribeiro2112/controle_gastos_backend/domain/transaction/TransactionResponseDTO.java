package com.carolribeiro2112.controle_gastos_backend.domain.transaction;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record TransactionResponseDTO(
        String id,
        String userId,
        String description,
        BigDecimal value,
        TransactionType type,
        TransactionCategory category,
        LocalDateTime transactionDate
) {
}

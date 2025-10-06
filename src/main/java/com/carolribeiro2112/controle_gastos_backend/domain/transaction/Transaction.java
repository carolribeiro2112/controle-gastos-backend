package com.carolribeiro2112.controle_gastos_backend.domain.transaction;

import com.carolribeiro2112.controle_gastos_backend.domain.user.User;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "transactions")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Enumerated(EnumType.STRING)
    private TransactionType type;

    @Enumerated(EnumType.STRING)
    private TransactionCategory category;

    private BigDecimal value;

    private String description;

    @Column(name = "transaction_date")
    private LocalDateTime transactionDate = LocalDateTime.now();
}

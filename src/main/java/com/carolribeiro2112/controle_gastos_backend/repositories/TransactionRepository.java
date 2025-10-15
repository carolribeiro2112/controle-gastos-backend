package com.carolribeiro2112.controle_gastos_backend.repositories;

import com.carolribeiro2112.controle_gastos_backend.domain.transaction.Transaction;
import com.carolribeiro2112.controle_gastos_backend.domain.transaction.TransactionCategory;
import com.carolribeiro2112.controle_gastos_backend.domain.transaction.TransactionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, String> {
    @Query("SELECT t FROM Transaction t WHERE t.user.id = :userId " +
            "AND (:categories IS NULL OR t.category IN :categories) " +
            "AND (:type IS NULL OR t.type = :type)")
    List<Transaction> findByFilters(
            @Param("userId") String userId,
            @Param("categories") List<TransactionCategory> categories,
            @Param("type") TransactionType type
    );
}

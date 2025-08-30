package com.carolribeiro2112.controle_gastos_backend.repositories;

import com.carolribeiro2112.controle_gastos_backend.domain.transaction.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, String> {
    List<Transaction> findByUser_Id(String userId);
}

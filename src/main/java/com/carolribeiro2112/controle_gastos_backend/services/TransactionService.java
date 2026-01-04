package com.carolribeiro2112.controle_gastos_backend.services;

import com.carolribeiro2112.controle_gastos_backend.domain.transaction.*;
import com.carolribeiro2112.controle_gastos_backend.repositories.TransactionRepository;
import com.carolribeiro2112.controle_gastos_backend.repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class TransactionService {
    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private UserRepository userRepository;

    public Transaction saveTransaction(String userId, TransactionType type, String description, TransactionCategory category, BigDecimal value) {
        var user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Transaction transaction = new Transaction();
        transaction.setUser(user);
        transaction.setType(type);
        transaction.setDescription(description);
        transaction.setCategory(category);
        transaction.setValue(value);
        transaction.setTransactionDate(LocalDateTime.now());

        return transactionRepository.save(transaction);
    }

    public Page<TransactionResponseDTO> getFilteredTransactions(String userId, List<TransactionCategory> categories, TransactionType type, Pageable pageable) {
        Page<Transaction> transactions = transactionRepository.findByFilters(userId, categories, type, pageable);
        return transactions
                .map(t -> new TransactionResponseDTO(
                        t.getId(),
                        t.getUser().getId(),
                        t.getDescription(),
                        t.getValue(),
                        t.getType(),
                        t.getCategory(),
                        t.getTransactionDate()
                ));
    }

    public List<TransactionResponseDTO> getFilteredTransactionsNoPagination(
            String userId,
            TransactionType type
    ) {
        List<Transaction> transactions = transactionRepository.findAll(userId);
        return transactions.stream()
                .map(t -> new TransactionResponseDTO(
                        t.getId(),
                        t.getUser().getId(),
                        t.getDescription(),
                        t.getValue(),
                        t.getType(),
                        t.getCategory(),
                        t.getTransactionDate()
                ))
                .toList();
    }

    public Transaction getTransactionById(String id) {
        return transactionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Transação com ID " + id + " não encontrada."));
    }

    private TransactionResponseDTO toResponseDTO(Transaction transaction) {
        return new TransactionResponseDTO(
                transaction.getId(),
                transaction.getUser().getId(),
                transaction.getDescription(),
                transaction.getValue(),
                transaction.getType(),
                transaction.getCategory(),
                transaction.getTransactionDate()
        );
    }

    public void deleteTransactionById(String id) {
        if (!transactionRepository.existsById(id)) {
            throw new EntityNotFoundException("Transação com ID " + id + " não encontrada.");
        }
        transactionRepository.deleteById(id);
    }
}

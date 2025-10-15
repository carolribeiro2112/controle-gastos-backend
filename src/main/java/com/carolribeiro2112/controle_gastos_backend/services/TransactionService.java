package com.carolribeiro2112.controle_gastos_backend.services;

import com.carolribeiro2112.controle_gastos_backend.domain.transaction.*;
import com.carolribeiro2112.controle_gastos_backend.repositories.TransactionRepository;
import com.carolribeiro2112.controle_gastos_backend.repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

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

    public List<TransactionResponseDTO> getFilteredTransactions(String userId, List<TransactionCategory> categories, TransactionType type) {
        List<Transaction> transactions = transactionRepository.findByFilters(userId, categories, type);
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

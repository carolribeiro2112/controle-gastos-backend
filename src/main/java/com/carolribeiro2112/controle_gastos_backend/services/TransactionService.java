package com.carolribeiro2112.controle_gastos_backend.services;

import com.carolribeiro2112.controle_gastos_backend.domain.transaction.Transaction;
import com.carolribeiro2112.controle_gastos_backend.domain.transaction.TransactionDTO;
import com.carolribeiro2112.controle_gastos_backend.domain.transaction.TransactionResponseDTO;
import com.carolribeiro2112.controle_gastos_backend.domain.transaction.TransactionType;
import com.carolribeiro2112.controle_gastos_backend.repositories.TransactionRepository;
import com.carolribeiro2112.controle_gastos_backend.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TransactionService {
    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private UserRepository userRepository;

    public Transaction saveTransaction(String userId, TransactionType type, String description, BigDecimal value) {
        var user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Transaction transaction = new Transaction();
        transaction.setUser(user);
        transaction.setType(type);
        transaction.setDescription(description);
        transaction.setValue(value);
        transaction.setTransactionDate(LocalDateTime.now());

        return transactionRepository.save(transaction);
    }

    public List<TransactionResponseDTO> getAllTransactionsByUserId(String userId) {
        List<Transaction> transactions = transactionRepository.findByUserId(userId);
        return transactions.stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    private TransactionResponseDTO toResponseDTO(Transaction transaction) {
        return new TransactionResponseDTO(
                transaction.getUser().getId(),
                transaction.getDescription(),
                transaction.getValue(),
                transaction.getType(),
                transaction.getTransactionDate()
        );
    }
}

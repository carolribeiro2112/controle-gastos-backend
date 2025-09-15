package com.carolribeiro2112.controle_gastos_backend.controllers;

import com.carolribeiro2112.controle_gastos_backend.domain.adminUserRelation.AdminUserRelation;
import com.carolribeiro2112.controle_gastos_backend.domain.transaction.Transaction;
import com.carolribeiro2112.controle_gastos_backend.domain.transaction.TransactionDTO;
import com.carolribeiro2112.controle_gastos_backend.domain.transaction.TransactionResponseDTO;
import com.carolribeiro2112.controle_gastos_backend.repositories.AdminUserRelationRepository;
import com.carolribeiro2112.controle_gastos_backend.services.TransactionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/transaction")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private AdminUserRelationRepository relationRepository;

    private void verifyAdminAccess(String adminId, String userId) {
        if(adminId.equals(userId)) {
            return;
        }
        List<AdminUserRelation> relations = relationRepository.findByAdminId(adminId);
        boolean isAdminOfUser = relations.stream()
                .anyMatch(relation -> relation.getUser().getId().equals(userId));

        if (!isAdminOfUser) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Admin não autorizado para este usuário");
        }
    }

    @PostMapping
    public  ResponseEntity<Transaction> createTransaction(
            @RequestHeader(value = "adminId", required = false) String adminId,
            @Valid @RequestBody TransactionDTO transactionDTO
    ) {
        verifyAdminAccess(adminId, transactionDTO.userId());

        Transaction transaction = transactionService.saveTransaction(
                transactionDTO.userId(),
                transactionDTO.type(),
                transactionDTO.description(),
                transactionDTO.value()
        );
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping
    public ResponseEntity<List<TransactionResponseDTO>> getAllTransactionsByUser(
            @RequestHeader(value = "adminId", required = false) String adminId,
            @RequestParam String userId
    ) {
        verifyAdminAccess(adminId, userId);

        List<TransactionResponseDTO> transaction = transactionService.getAllTransactionsByUserId(userId);
        return ResponseEntity.ok(transaction);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTransaction(
            @RequestHeader(value = "adminId", required = false) String adminId,
            @PathVariable String id
    ) {
        Transaction transaction = transactionService.getTransactionById(id);
        verifyAdminAccess(adminId, transaction.getUser().getId());

        transactionService.deleteTransactionById(id);
        return ResponseEntity.noContent().build();
    }
}
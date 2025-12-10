package com.carolribeiro2112.controle_gastos_backend.controllers;

import com.carolribeiro2112.controle_gastos_backend.domain.adminUserRelation.AdminUserRelation;
import com.carolribeiro2112.controle_gastos_backend.domain.transaction.*;
import com.carolribeiro2112.controle_gastos_backend.repositories.AdminUserRelationRepository;
import com.carolribeiro2112.controle_gastos_backend.services.TransactionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/transaction")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private AdminUserRelationRepository relationRepository;

    private void verifyAdminAccess(String adminId, String userId) {
        // Se adminId for nulo, assume que o próprio usuário está acessando seus dados
        if (adminId == null) {
            // Aqui você pode adicionar uma verificação de segurança, se necessário
            return;
        }

        // Se adminId for igual ao userId, é o próprio admin acessando seus dados
        if (Objects.equals(adminId, userId)) {
            return;
        }

        // Verifica se o admin está vinculado ao usuário
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
                transactionDTO.category(),
                transactionDTO.value()
        );
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping
    public ResponseEntity<Page<TransactionResponseDTO>> getAllTransactionsByUser(
            @RequestHeader(value = "adminId", required = false) String adminId,
            @RequestParam String userId,
            @RequestParam(required = false) List<TransactionCategory> category,
            @RequestParam(required = false) TransactionType type,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "transactionDate") String sortBy,
            @RequestParam(defaultValue = "DESC") String sortDirection
    ) {
        verifyAdminAccess(adminId, userId);

        Sort sort = sortDirection.equalsIgnoreCase("DESC")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);

        Page<TransactionResponseDTO> transaction = transactionService.getFilteredTransactions(userId, category, type, pageable);
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
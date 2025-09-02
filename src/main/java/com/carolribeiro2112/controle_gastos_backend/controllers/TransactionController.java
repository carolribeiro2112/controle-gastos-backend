package com.carolribeiro2112.controle_gastos_backend.controllers;

import com.carolribeiro2112.controle_gastos_backend.domain.transaction.Transaction;
import com.carolribeiro2112.controle_gastos_backend.domain.transaction.TransactionDTO;
import com.carolribeiro2112.controle_gastos_backend.services.TransactionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/transaction")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @PostMapping
    public  ResponseEntity<Transaction> createTransaction(@Valid @RequestBody TransactionDTO transactionDTO) {
        Transaction transaction = transactionService.saveTransaction(
                transactionDTO.userId(),
                transactionDTO.type(),
                transactionDTO.description(),
                transactionDTO.value()
        );
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
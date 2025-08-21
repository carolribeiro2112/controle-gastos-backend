package com.carolribeiro2112.controle_gastos_backend.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TransactionController {

    @GetMapping("/transaction")
    public ResponseEntity<String> getLoginMessage() {
        return ResponseEntity.ok("You logged in");
    }
}
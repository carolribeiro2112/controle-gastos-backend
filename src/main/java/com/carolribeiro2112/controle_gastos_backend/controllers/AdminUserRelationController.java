package com.carolribeiro2112.controle_gastos_backend.controllers;

import com.carolribeiro2112.controle_gastos_backend.domain.adminUserRelation.AdminUserRelation;
import com.carolribeiro2112.controle_gastos_backend.domain.adminUserRelation.BindRequestDTO;
import com.carolribeiro2112.controle_gastos_backend.domain.adminUserRelation.BindResponseDTO;
import com.carolribeiro2112.controle_gastos_backend.repositories.AdminUserRelationRepository;
import com.carolribeiro2112.controle_gastos_backend.services.AdminUserRelationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/admin")
public class AdminUserRelationController {
    @Autowired
    private AdminUserRelationService adminUserService;

    @Autowired
    private AdminUserRelationRepository relationRepository;

    @PostMapping("/bind")
    public ResponseEntity<String> bindAdminToUser(@RequestBody BindRequestDTO bindRequestDTO) {
        adminUserService.bindAdminToUser(bindRequestDTO.adminId(), bindRequestDTO.userId());
        return ResponseEntity.ok("Admin vinculado ao usuário com sucesso");
    }

    @GetMapping("/admin-relations")
    public ResponseEntity<List<BindResponseDTO>> getAllRelations() {
        List<AdminUserRelation> relations = relationRepository.findAll();

        List<BindResponseDTO> response = relations.stream()
                .map(rel -> new BindResponseDTO(rel.getAdmin().getId(), rel.getUser().getId(), rel.getAdmin().getLogin(), rel.getUser().getLogin()))
                .collect(Collectors.toList());

        return ResponseEntity.ok(response);
    }

    @GetMapping("/relations-by-admin/{adminId}")
    public ResponseEntity<List<BindResponseDTO>> getRelationsByAdmin(@PathVariable String adminId) {
        List<AdminUserRelation> relations = relationRepository.findByAdminId(adminId);
        List<BindResponseDTO> response = relations.stream()
                .map(rel -> new BindResponseDTO(rel.getAdmin().getId(), rel.getAdmin().getLogin(), rel.getUser().getId(), rel.getUser().getLogin()))
                .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }
}

package com.carolribeiro2112.controle_gastos_backend.services;

import com.carolribeiro2112.controle_gastos_backend.domain.adminUserRelation.AdminUserRelation;
import com.carolribeiro2112.controle_gastos_backend.domain.user.User;
import com.carolribeiro2112.controle_gastos_backend.domain.user.UserRole;
import com.carolribeiro2112.controle_gastos_backend.repositories.AdminUserRelationRepository;
import com.carolribeiro2112.controle_gastos_backend.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminUserRelationService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AdminUserRelationRepository relationRepository;

    public void bindAdminToUser(String adminId, String userId) {
        User admin = userRepository.findById(adminId)
                .orElseThrow(() -> new RuntimeException("Admin não encontrado"));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User não encontrado"));

        if (admin.getRole() != UserRole.ADMIN || user.getRole() != UserRole.USER) {
            throw new RuntimeException("Tipos de usuário inválidos para relacionamento");
        }

        AdminUserRelation rel = new AdminUserRelation();
        rel.setAdmin(admin);
        rel.setUser(user);
        rel.setAdminLogin(admin.getLogin());
        rel.setUserLogin(user.getLogin());

        relationRepository.save(rel);
    }
}

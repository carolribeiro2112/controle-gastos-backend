package com.carolribeiro2112.controle_gastos_backend.repositories;

import com.carolribeiro2112.controle_gastos_backend.domain.adminUserRelation.AdminUserRelation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AdminUserRelationRepository extends JpaRepository<AdminUserRelation, String> {
    List<AdminUserRelation> findByAdminId(String adminId);
}

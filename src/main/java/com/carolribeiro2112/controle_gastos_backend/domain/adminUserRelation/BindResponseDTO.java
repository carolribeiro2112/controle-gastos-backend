package com.carolribeiro2112.controle_gastos_backend.domain.adminUserRelation;

public record BindResponseDTO(
        String adminId,
        String adminLogin,
        String userId,
        String userLogin
) {
}

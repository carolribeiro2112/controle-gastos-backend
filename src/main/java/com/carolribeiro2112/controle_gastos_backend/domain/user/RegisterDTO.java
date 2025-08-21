package com.carolribeiro2112.controle_gastos_backend.domain.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record RegisterDTO(
        @NotBlank String login,
        @NotBlank String password,
        @NotNull UserRole role
) {}

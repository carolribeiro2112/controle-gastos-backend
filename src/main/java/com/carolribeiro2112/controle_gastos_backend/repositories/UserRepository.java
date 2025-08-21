package com.carolribeiro2112.controle_gastos_backend.repositories;

import com.carolribeiro2112.controle_gastos_backend.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

public interface UserRepository extends JpaRepository<User, String> {
    UserDetails findByLogin(String login);
}

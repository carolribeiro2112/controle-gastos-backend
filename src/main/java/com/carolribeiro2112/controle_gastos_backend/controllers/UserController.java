package com.carolribeiro2112.controle_gastos_backend.controllers;

import com.carolribeiro2112.controle_gastos_backend.domain.user.User;
import com.carolribeiro2112.controle_gastos_backend.domain.user.UserResponseDTO;
import com.carolribeiro2112.controle_gastos_backend.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/{login}")
    public ResponseEntity<UserResponseDTO> getUserByLogin(@PathVariable String login) {
        User user = (User) userRepository.findByLogin(login);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        var userResponseDTO = new UserResponseDTO(user.getId(), user.getLogin(), user.getAge(), user.getRole());
        return ResponseEntity.ok(userResponseDTO);
    }
}

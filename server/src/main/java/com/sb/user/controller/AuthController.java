package com.sb.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sb.infra.security.role.Authentication;
import com.sb.user.dto.AuthManagerResponseDTO;
import com.sb.user.dto.AuthResponseDTO;
import com.sb.user.dto.CreateRequestDTO;
import com.sb.user.dto.LoginRequestDTO;
import com.sb.user.enums.RoleEnum;
import com.sb.user.service.UserService;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @Transactional
    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@RequestBody @Valid LoginRequestDTO data) {
        AuthResponseDTO response = this.userService.login(data);
        return ResponseEntity.ok(response);
    }

    @Transactional
    @PostMapping("/create")
    @Authentication(role = RoleEnum.ADMIN)
    public ResponseEntity<AuthResponseDTO> create(@RequestBody @Valid CreateRequestDTO data) {
        AuthResponseDTO user = this.userService.create(data);
        return ResponseEntity.ok(user);

    }

    @Transactional
    @GetMapping("/auth-manager/{code}")
    @Authentication(role = RoleEnum.SELLER)
    public ResponseEntity<AuthManagerResponseDTO> authManager(@PathVariable("code") String code) {
        AuthManagerResponseDTO response = this.userService.authManager(code);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/me")
    public ResponseEntity<String> me() {
        return ResponseEntity.ok("Sucesso");
    }
}

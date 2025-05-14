package com.sb.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sb.user.dtos.AuthResponseDTO;
import com.sb.user.dtos.CreateRequestDTO;
import com.sb.user.dtos.LoginRequestDTO;
import com.sb.user.service.UserService;


@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@RequestBody LoginRequestDTO data) {
        AuthResponseDTO user = this.userService.login(data);
        return ResponseEntity.ok(user);
    }

    @PostMapping("/create")
    public ResponseEntity<AuthResponseDTO> create(@RequestBody CreateRequestDTO data) {
        AuthResponseDTO user = this.userService.create(data);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/me") 
    public ResponseEntity<String> me() {
        return ResponseEntity.ok("Sucesso");
    }
}


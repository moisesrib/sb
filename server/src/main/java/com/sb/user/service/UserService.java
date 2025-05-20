package com.sb.user.service;

import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sb.core.exceptions.EmailAlreadyExistsException;
import com.sb.core.exceptions.NotFoundException;
import com.sb.infra.security.TokenService;
import com.sb.user.dto.AuthManagerResponseDTO;
import com.sb.user.dto.AuthResponseDTO;
import com.sb.user.dto.CreateRequestDTO;
import com.sb.user.dto.LoginRequestDTO;
import com.sb.user.model.Role;
import com.sb.user.model.User;
import com.sb.user.repository.RoleRepository;
import com.sb.user.repository.UserRepository;
import com.sb.utils.BarcodeUtils;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;

    public AuthResponseDTO login(LoginRequestDTO data) {
        User user = this.userRepository.findByEmail(data.email()).orElseThrow(() -> new NotFoundException("Usuário não encontrado"));
        if (!this.passwordEncoder.matches(data.password(), user.getPassword())) {
            throw new NotFoundException("E-mail ou senha inválidos");
        }
        String token = this.tokenService.generateToken(user);
        String roleName = user.getRole() != null ? user.getRole().getName().toString() : null;
        return new AuthResponseDTO(user.getName(), user.getEmail(), token, roleName, user.getCode());
    }
     
    public AuthResponseDTO create(CreateRequestDTO data) {
        Optional<User> existingUser = this.userRepository.findByEmail(data.email());

        if(existingUser.isPresent()) {
            throw new EmailAlreadyExistsException("E-mail já está em uso");
        }

        User newUser = new User();
        newUser.setName(data.name());
        newUser.setEmail(data.email());
        newUser.setPassword(this.passwordEncoder.encode(data.password()));
        newUser.setCode(BarcodeUtils.generateCode128Barcode());
        newUser.setActive(true);
        
        // Role userRole = roleRepository.findByName("USER")
        //     .orElseThrow(() -> new NotFoundException("Role USER não encontrada"));
        newUser.setRole(null);
        
        this.userRepository.save(newUser);

        String token = this.tokenService.generateToken(newUser);
        return new AuthResponseDTO(newUser.getName(), newUser.getEmail(), token, null, newUser.getCode());
    }

    public AuthManagerResponseDTO authManager(String code) {
       User user = this.userRepository.findByCode(code)
        .orElseThrow(() -> new NotFoundException("Usuário não encontrado"));
        String token = this.tokenService.generateToken(user);
        return new AuthManagerResponseDTO(user.getName(), user.getEmail(), user.getCode(), user.getRole().getName().toString(), token);
    }
}

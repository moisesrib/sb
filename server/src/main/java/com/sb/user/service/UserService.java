package com.sb.user.service;

import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.sb.core.exceptions.EmailAlreadyExistsException;
import com.sb.core.exceptions.NotFoundException;
import com.sb.infra.security.TokenService;
import com.sb.user.dto.AuthResponseDTO;
import com.sb.user.dto.CreateRequestDTO;
import com.sb.user.dto.LoginRequestDTO;
import com.sb.user.model.User;
import com.sb.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;

    public AuthResponseDTO login(LoginRequestDTO data) {
        User user = this.userRepository.findByEmail(data.email()).orElseThrow(() -> new NotFoundException("Usuário não encontrado"));
        if (!this.passwordEncoder.matches(data.password(), user.getPassword())) {
            throw new NotFoundException("E-mail ou senha inválidos");
        }
        String token = this.tokenService.generateToken(user);
        return new AuthResponseDTO(user.getName(), user.getEmail(), token);
    }
    

    public AuthResponseDTO create(CreateRequestDTO data) {
        Optional<User> user = this.userRepository.findByEmail(data.email());

        if(user.isPresent()) {
            throw new EmailAlreadyExistsException("E-mail já está em uso");
        }

        User newUser = new User();
        newUser.setName(data.name());
        newUser.setEmail(data.email());
        newUser.setPassword(this.passwordEncoder.encode(data.password()));
        this.userRepository.save(newUser);


        String token = this.tokenService.generateToken(newUser);
        return new AuthResponseDTO(newUser.getName(), newUser.getEmail(), token);
    }

}

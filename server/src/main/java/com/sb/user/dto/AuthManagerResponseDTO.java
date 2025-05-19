package com.sb.user.dto;

public record AuthManagerResponseDTO(
    String name, 
    String email, 
    String code,
    String role,
    String token
) {}

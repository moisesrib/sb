package com.sb.user.repository;

import java.util.Optional;
import java.util.UUID;

import com.sb.user.model.User;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, UUID> {

    Optional<User> findByEmail(String email);
    Optional<User> findByCode(String code);
}

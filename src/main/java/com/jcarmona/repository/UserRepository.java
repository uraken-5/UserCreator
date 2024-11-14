package com.jcarmona.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jcarmona.model.User;

import java.util.Optional;
import java.util.UUID;


public interface UserRepository extends JpaRepository<User, UUID> {
    User findByEmail(String email);
    Optional<User> findById(UUID userId);
}

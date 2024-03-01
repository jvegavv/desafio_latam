package com.example.DesafioBCI.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.DesafioBCI.entity.Usuario;

@Repository
public interface UserRepository extends JpaRepository<Usuario, UUID> {
    boolean existsByEmail(String email);
}

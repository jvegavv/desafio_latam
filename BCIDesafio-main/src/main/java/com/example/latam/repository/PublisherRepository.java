package com.example.latam.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.latam.entity.Mensaje;

@Repository
public interface PublisherRepository extends JpaRepository<Mensaje, UUID> {
    Mensaje save(Mensaje mensaje);
}

package com.example.latam.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.latam.entity.Tema;

@Repository
public interface TemaRepository extends JpaRepository<Tema, Integer> {
    Tema save(Tema mensaje);
}

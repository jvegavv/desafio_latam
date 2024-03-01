package com.example.latam.entity;

import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
@Entity
public class Mensaje {
	
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
	
    @NotBlank
    private String texto;

}


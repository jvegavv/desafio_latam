package com.example.latam.entity;

import java.util.List;
import java.util.UUID;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
@Entity
public class Tema {
	
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
	
    @NotBlank
    @NotNull
    private String nombre;

    @OneToMany(cascade = CascadeType.ALL)
	private List<Mensaje> mensajes;

}


package com.example.latam.controller;


import java.util.Collections;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.latam.entity.Tema;
import com.example.latam.service.TemaService;

import jakarta.validation.constraints.Null;

import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/mensaje")
public class TemaController {

    Logger logger = LoggerFactory.getLogger(TemaController.class);

    @Autowired
    private TemaService temaService;


    @PostMapping
    public ResponseEntity<?> crearTema(@RequestBody Tema tema) {
        try {
           
            logger.info("Tema a crear "+tema);
            temaService.save(tema);

            return ResponseEntity.status(HttpStatus.CREATED).body(null);

        } catch (Exception e) {
        	return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Collections.singletonMap("mensaje", e.getMessage()));
        }
    }

    @GetMapping("/tema/{id}")
    public ResponseEntity<?> obtenerMensajes(@PathVariable Integer id) {
        try {
           
            logger.info("Mensajes del tema a obtener "+id);
            Optional<Tema> tema = temaService.get(id);

            if (tema.isPresent())
                return ResponseEntity.status(HttpStatus.FOUND).body(tema.get().getMensajes());
            else        
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.singletonMap("mensaje", "Tema "+id+" No existe"));

        } catch (Exception e) {
        	return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Collections.singletonMap("mensaje", e.getMessage()));
        }
    }


}

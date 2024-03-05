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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.latam.entity.Mensaje;
import com.example.latam.entity.Tema;
import com.example.latam.service.TemaService;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/tema")
public class TemaController {

    Logger logger = LoggerFactory.getLogger(TemaController.class);
    private final String version = "1.0";

    @Autowired
    private TemaService temaService;


    @PostMapping
    public ResponseEntity<?> crearTema(@RequestBody Tema tema) {
        try {
           
            logger.info("["+version+"] Tema a crear "+tema);
            tema.setId(null);
            
            Optional<Tema> temaOptional = Optional.of(temaService.save(tema));

            if (temaOptional.isPresent())
                return ResponseEntity.status(HttpStatus.CREATED).body(Collections.singletonMap("Id del tema creado", temaOptional.get().getId()));
            else
                return ResponseEntity.status(HttpStatus.CONFLICT).body(Collections.singletonMap("mensaje", "ERROR al crear el tema "+ tema.getNombre())); 


        } catch (Exception e) {
        	return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Collections.singletonMap("mensaje", e.getMessage()));
        }
    }


    @PutMapping
    public ResponseEntity<?> actualizarTema(@RequestBody Tema tema) {
        try {
           
            logger.info("["+version+"] Tema a actualizar "+tema);

            Optional<Tema> temaOptional = temaService.get(tema.getId());
           
            logger.info("["+version+"] Tema a obtenido BD "+temaOptional.get());

            if (temaOptional.isPresent()){

                temaOptional = Optional.of(temaService.save(tema));

                if (temaOptional.isPresent())
                    return ResponseEntity.status(HttpStatus.OK).body(Collections.singletonMap("Tema actualizado", temaOptional.get()));
                else
                    return ResponseEntity.status(HttpStatus.CONFLICT).body(Collections.singletonMap("mensaje", "ERROR al actualizar el tema "+ tema.getNombre())); 
            
        
            }else{
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.singletonMap("mensaje", "Tema "+tema.getId()+" No existe"));

            }    

        } catch (Exception e) {
        	return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Collections.singletonMap("mensaje", e.getMessage()));
        }
    }


    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerMensajes(@PathVariable Integer id) {
        try {
           
            logger.info("["+version+"] Mensajes del tema a obtener "+id);
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

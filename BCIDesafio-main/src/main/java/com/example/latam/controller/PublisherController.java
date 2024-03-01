package com.example.latam.controller;


import java.util.Collections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.latam.service.PublisherService;

@RestController
@RequestMapping("/api/users")
public class PublisherController {

    @Autowired
    private PublisherService publisherService;


    @PostMapping(value = "/create")
    public ResponseEntity<?> createUser() {
        try {
           
                System.out.println("EN EL CONTROLLER");
                publisherService.createMessage("HOLA");

            return ResponseEntity.status(HttpStatus.CREATED).body(null);

        } catch (Exception e) {
        	return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Collections.singletonMap("mensaje", e.getMessage()));
        }
    }


}

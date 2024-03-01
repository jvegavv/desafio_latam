package com.example.DesafioBCI.controller;


import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.DesafioBCI.entity.Usuario;
import com.example.DesafioBCI.exception.UserAlreadyExistsException;
import com.example.DesafioBCI.service.UserService;
import com.example.DesafioBCI.utils.EmailValidator;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;
    private final EmailValidator emailValidator;

    @Autowired
    public UserController(UserService userService, EmailValidator emailValidator) {
        this.userService = userService;
        this.emailValidator = emailValidator;
    }

    @PostMapping(value = "/create")
    public ResponseEntity<?> createUser(@RequestBody Usuario user) {
        try {
            // Validar el formato del correo electrónico antes de llamar al servicio
            String email = user.getEmail();
            if (!emailValidator.isValidEmail(email)) {
            	return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Collections.singletonMap("mensaje", "Formato de correo electrónico no válido"));
            }

            // Llamar al servicio para crear el usuario
            Usuario createdUser = userService.createUser(user);

            // Retornar la respuesta con el usuario creado
            return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
        } catch (UserAlreadyExistsException e) {
        	return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Collections.singletonMap("mensaje", e.getMessage()));
        }
    }


}

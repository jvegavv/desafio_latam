package com.example.DesafioBCI.service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.DesafioBCI.entity.Usuario;
import com.example.DesafioBCI.exception.UserAlreadyExistsException;
import com.example.DesafioBCI.repository.UserRepository;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
	@Autowired
	private UserRepository userRepository;

	public Usuario createUser(Usuario user) {

		if (userRepository.existsByEmail(user.getEmail())) {
			throw new UserAlreadyExistsException("El correo ya registrado");
		}

		String token = generateToken();

		LocalDateTime now = LocalDateTime.now();
		user.setCreated(now);
		user.setModified(now);
		user.setLastLogin(now);
		user.setToken(token);
		user.setActive(true);

		return userRepository.save(user);
	}

	private String generateToken() {

		String secretKey = "secreto123";

		SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

		LocalDateTime now = LocalDateTime.now();
		LocalDateTime expirationTime = now.plusDays(1); // Token expirará en 1 día

		return Jwts.builder().setSubject("user").setIssuedAt(Date.from(now.atZone(ZoneId.systemDefault()).toInstant())) // Fecha
																														// emisión
				.setExpiration(Date.from(expirationTime.atZone(ZoneId.systemDefault()).toInstant())) // Fecha de
																										// expiración
				.compact();
	}

}
package com.example.latam.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.latam.entity.Mensaje;
import com.example.latam.repository.PublisherRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PublisherService {
	@Autowired
	private PublisherRepository publisherRepository;

	public Mensaje createMessage(String message) {

		System.out.println("EN EL SERVICE");
		Mensaje mensaje = new Mensaje();
		mensaje.setTexto(message);

		return publisherRepository.save(mensaje);
	}

}
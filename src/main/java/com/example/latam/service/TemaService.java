package com.example.latam.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.latam.entity.Tema;
import com.example.latam.repository.TemaRepository;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
@RequiredArgsConstructor
public class TemaService {

	@Autowired
	private TemaRepository temaRepository;

	Logger logger = LoggerFactory.getLogger(TemaService.class);

	public Tema save(Tema tema) {

		logger.info("Tema a crear "+tema);
		return temaRepository.save(tema);
	}

    public Optional<Tema> get(Integer id) {

		logger.info("Mensajes del tema a obtener "+id);
		return temaRepository.findById(id);

    }

}
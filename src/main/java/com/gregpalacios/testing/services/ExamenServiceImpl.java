package com.gregpalacios.testing.services;

import java.util.List;
import java.util.Optional;

import com.gregpalacios.testing.models.Examen;
import com.gregpalacios.testing.repositories.ExamenRepository;
import com.gregpalacios.testing.repositories.PreguntaRepository;

public class ExamenServiceImpl implements ExamenService {

	private ExamenRepository examenRepository;

	private PreguntaRepository preguntaRepository;

	public ExamenServiceImpl(ExamenRepository examenRepository, PreguntaRepository preguntaRepository) {
		this.examenRepository = examenRepository;
		this.preguntaRepository = preguntaRepository;
	}

	@Override
	public Optional<Examen> findExamenPorNombre(String nombre) {
		return examenRepository.findAll().stream().filter(e -> e.getNombre().contains(nombre)).findFirst();
	}

	@Override
	public Examen findExamenPorNombreConPreguntas(String nombre) {
		Optional<Examen> examenOptional = findExamenPorNombre(nombre);
		Examen examen = null;
		if (examenOptional.isPresent()) {
			examen = examenOptional.orElseThrow();
			List<String> preguntas = preguntaRepository.findPreguntasPorExamenId(examen.getId());
			examen.setPreguntas(preguntas);
		}

		return examen;
	}

	@Override
	public Examen guardar(Examen examen) {
		if (!examen.getPreguntas().isEmpty()) {
			preguntaRepository.guardarVariasPreguntas(examen.getPreguntas());
		}

		return examenRepository.guardar(examen);
	}

}

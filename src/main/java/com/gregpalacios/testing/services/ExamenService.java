package com.gregpalacios.testing.services;

import java.util.Optional;

import com.gregpalacios.testing.models.Examen;

public interface ExamenService {

	Optional<Examen> findExamenPorNombre(String nombre);

	Examen findExamenPorNombreConPreguntas(String nombre);
	
	Examen guardar(Examen examen);
}

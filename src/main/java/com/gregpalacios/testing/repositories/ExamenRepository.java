package com.gregpalacios.testing.repositories;

import java.util.List;

import com.gregpalacios.testing.models.Examen;

public interface ExamenRepository {

	List<Examen> findAll();
	
	Examen guardar(Examen examen);
}

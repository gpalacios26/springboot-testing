package com.gregpalacios.testing.repositories;

import java.util.List;

public interface PreguntaRepository {

	List<String> findPreguntasPorExamenId(Long id);
	
	void guardarVariasPreguntas(List<String> preguntas);
}

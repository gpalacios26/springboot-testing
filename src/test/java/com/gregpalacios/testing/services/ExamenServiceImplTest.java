package com.gregpalacios.testing.services;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import static org.mockito.ArgumentMatchers.any;

import com.gregpalacios.testing.models.Examen;
import com.gregpalacios.testing.repositories.ExamenRepository;
import com.gregpalacios.testing.repositories.PreguntaRepository;

class ExamenServiceImplTest {

	ExamenRepository examenRepository;

	PreguntaRepository preguntaRepository;

	ExamenService service;

	@BeforeEach
	void setUp() throws Exception {
		examenRepository = Mockito.mock(ExamenRepository.class);
		preguntaRepository = Mockito.mock(PreguntaRepository.class);
		service = new ExamenServiceImpl(examenRepository, preguntaRepository);
	}

	@Test
	void testFindExamenPorNombre() {
		Mockito.when(examenRepository.findAll()).thenReturn(Datos.EXAMENES);
		Optional<Examen> examen = service.findExamenPorNombre("Matemáticas");

		Assertions.assertTrue(examen.isPresent());
		Assertions.assertEquals(5L, examen.orElseThrow().getId());
		Assertions.assertEquals("Matemáticas", examen.orElseThrow().getNombre());
	}

	@Test
	void testFindExamenPorNombreListaVacia() {
		List<Examen> datos = Collections.emptyList();
		Mockito.when(examenRepository.findAll()).thenReturn(datos);
		Optional<Examen> examen = service.findExamenPorNombre("Matemáticas");

		Assertions.assertFalse(examen.isPresent());
	}

	@Test
	void testPreguntasExamen() {
		Mockito.when(examenRepository.findAll()).thenReturn(Datos.EXAMENES);
		Mockito.when(preguntaRepository.findPreguntasPorExamenId(any())).thenReturn(Datos.PREGUNTAS);
		Examen examen = service.findExamenPorNombreConPreguntas("Matemáticas");

		Assertions.assertEquals(5, examen.getPreguntas().size());
		Assertions.assertTrue(examen.getPreguntas().contains("Integrales"));
	}

	@Test
	void testPreguntasExamenVerify() {
		Mockito.when(examenRepository.findAll()).thenReturn(Datos.EXAMENES);
		Mockito.when(preguntaRepository.findPreguntasPorExamenId(any())).thenReturn(Datos.PREGUNTAS);
		Examen examen = service.findExamenPorNombreConPreguntas("Matemáticas");

		Assertions.assertEquals(5, examen.getPreguntas().size());
		Assertions.assertTrue(examen.getPreguntas().contains("Integrales"));

		Mockito.verify(examenRepository).findAll();
		Mockito.verify(preguntaRepository).findPreguntasPorExamenId(any());
	}

	@Test
	void testNoExisteExamenVerify() {
		Mockito.when(examenRepository.findAll()).thenReturn(Datos.EXAMENES);
		Mockito.when(preguntaRepository.findPreguntasPorExamenId(any())).thenReturn(Datos.PREGUNTAS);
		Examen examen = service.findExamenPorNombreConPreguntas("Matemáticas");

		Assertions.assertNotNull(examen);

		Mockito.verify(examenRepository).findAll();
		Mockito.verify(preguntaRepository).findPreguntasPorExamenId(any());
	}

	@Test
	void testGuardarExamen() {
		Examen newExamen = Datos.EXAMEN;
		newExamen.setPreguntas(Datos.PREGUNTAS);
		Mockito.when(examenRepository.guardar(any(Examen.class))).thenReturn(Datos.EXAMEN);
		Examen examen = service.guardar(newExamen);

		Assertions.assertNotNull(examen);
		Assertions.assertEquals(8L, examen.getId());

		Mockito.verify(examenRepository).guardar(any(Examen.class));
		Mockito.verify(preguntaRepository).guardarVariasPreguntas(any());
	}

}

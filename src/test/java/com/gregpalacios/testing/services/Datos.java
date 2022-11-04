package com.gregpalacios.testing.services;

import java.util.Arrays;
import java.util.List;

import com.gregpalacios.testing.models.Examen;

public class Datos {

	public final static List<Examen> EXAMENES = Arrays.asList(new Examen(5L, "Matemáticas"), new Examen(6L, "Lenguaje"),
			new Examen(7L, "Historia"));

	public final static List<String> PREGUNTAS = Arrays.asList("Arimética", "Integrales", "Derivadas", "Trigonometría",
			"Geometría");

	public final static Examen EXAMEN = new Examen(8L, "Física");
}

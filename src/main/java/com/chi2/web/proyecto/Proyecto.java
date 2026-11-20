package com.chi2.web.proyecto;

import java.util.List;

/**
 * Proyecto mostrado en el sitio: problema, solución, tecnologías y estado.
 * {@code resultado}: en los casos (trabajos terminados), qué se logró; en los proyectos en desarrollo,
 * en qué estado están. Va {@code null} si todavía no hay nada que contar.
 */
public record Proyecto(
		String slug,
		String nombre,
		String rubro,
		EstadoProyecto estado,
		String resumen,
		String problema,
		String solucion,
		List<String> tecnologias,
		String resultado) {

	public boolean esCaso() {
		return estado == EstadoProyecto.CASO;
	}

	/** Rótulo de {@code resultado} en el detalle: "Resultado" en un caso, "Estado actual" en el resto. */
	public String tituloResultado() {
		return esCaso() ? "Resultado" : "Estado actual";
	}

}

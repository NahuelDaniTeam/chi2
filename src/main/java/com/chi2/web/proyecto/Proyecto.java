package com.chi2.web.proyecto;

import java.util.List;

/**
 * Proyecto mostrado en el sitio: problema, solución, tecnologías y estado.
 * {@code resultado} aplica a los casos (trabajos terminados); en los proyectos en desarrollo va {@code null}.
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

}

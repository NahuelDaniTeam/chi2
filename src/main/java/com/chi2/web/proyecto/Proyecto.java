package com.chi2.web.proyecto;

import java.util.List;

/** Proyecto mostrado en el sitio: problema, solución propuesta, tecnologías y estado. */
public record Proyecto(
		String slug,
		String nombre,
		String rubro,
		EstadoProyecto estado,
		String resumen,
		String problema,
		String solucion,
		List<String> tecnologias) {
}

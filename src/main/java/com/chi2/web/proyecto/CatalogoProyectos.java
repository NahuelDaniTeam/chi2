package com.chi2.web.proyecto;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

/**
 * Proyectos del sitio. En la v1 no hay panel de administración: el contenido se edita acá
 * (docs/01-vision-alcance.md §6). Sin testimonios ni clientes ficticios.
 */
@Component
public class CatalogoProyectos {

	private static final List<Proyecto> PROYECTOS = List.of(
			new Proyecto(
					"barberia",
					"Sistema para barberías",
					"Barberías y peluquerías",
					EstadoProyecto.EN_DESARROLLO,
					"[PENDIENTE: resumen en una frase]",
					"[PENDIENTE: el problema del negocio, contado en concreto]",
					"[PENDIENTE: la solución propuesta]",
					List.of("Java", "Spring Boot", "PostgreSQL")),
			new Proyecto(
					"gestion-de-tareas",
					"Gestión de tareas",
					"Negocios que necesitan organizar tareas y planificación",
					EstadoProyecto.EN_DESARROLLO,
					"[PENDIENTE: resumen en una frase]",
					"[PENDIENTE: el problema del negocio, contado en concreto]",
					"[PENDIENTE: la solución propuesta]",
					List.of("Java", "Spring Boot", "PostgreSQL")));

	public List<Proyecto> todos() {
		return PROYECTOS;
	}

	public Optional<Proyecto> porSlug(String slug) {
		return PROYECTOS.stream().filter(p -> p.slug().equals(slug)).findFirst();
	}

}

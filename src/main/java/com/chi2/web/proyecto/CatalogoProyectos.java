package com.chi2.web.proyecto;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

/**
 * Proyectos del sitio. En la v1 no hay panel de administración: el contenido se edita acá
 * (docs/01-vision-alcance.md §6) a partir de docs/02-contenido.md §4.
 * Sin testimonios ni clientes ficticios; un cliente real se nombra solo con su permiso.
 */
@Component
public class CatalogoProyectos {

	private static final List<Proyecto> PROYECTOS = List.of(
			// Trabajos anteriores (casos). Detalle pendiente de Daniel: PENDIENTES.md 1, 2 y 3.
			new Proyecto(
					"camiones-recolectores",
					"Seguimiento de camiones recolectores",
					"Recolección de residuos urbanos",
					EstadoProyecto.CASO,
					"Prototipo para seguir los camiones recolectores de una ciudad.",
					"[PENDIENTE: qué le pasaba a la ciudad antes y qué no podían ver o controlar]",
					"[PENDIENTE: 3 o 4 funciones principales]",
					List.of("[PENDIENTE]"),
					"[PENDIENTE: prototipo, si se usó o presentó, números]"),
			new Proyecto(
					"imprenta",
					"Cálculo de medidas para una imprenta",
					"Imprentas",
					EstadoProyecto.CASO,
					"Sistema para calcular medidas en una imprenta.",
					"[PENDIENTE: qué se calculaba y cómo se hacía antes del sistema]",
					"[PENDIENTE: 3 o 4 funciones principales]",
					List.of("[PENDIENTE]"),
					"[PENDIENTE: estado y resultado]"),
			// Proyectos en desarrollo. Detalle pendiente de los dos: PENDIENTES.md 6.
			new Proyecto(
					"barberia",
					"Sistema para barberías",
					"Barberías y peluquerías",
					EstadoProyecto.EN_DESARROLLO,
					"[PENDIENTE: resumen en una frase]",
					"[PENDIENTE: el problema del negocio, contado en concreto]",
					"[PENDIENTE: 3 o 4 funciones principales]",
					List.of("Java", "Spring Boot", "PostgreSQL"),
					null),
			new Proyecto(
					"gestion-de-tareas",
					"Gestión de tareas",
					"Negocios que necesitan organizar tareas y planificación",
					EstadoProyecto.EN_DESARROLLO,
					"[PENDIENTE: resumen en una frase]",
					"[PENDIENTE: el problema del negocio, contado en concreto]",
					"[PENDIENTE: 3 o 4 funciones principales]",
					List.of("Java", "Spring Boot", "PostgreSQL"),
					null));

	public List<Proyecto> todos() {
		return PROYECTOS;
	}

	/** Trabajos terminados: la prueba de capacidad más fuerte, van primero. */
	public List<Proyecto> casos() {
		return PROYECTOS.stream().filter(Proyecto::esCaso).toList();
	}

	public List<Proyecto> enDesarrollo() {
		return PROYECTOS.stream().filter(p -> p.estado() == EstadoProyecto.EN_DESARROLLO).toList();
	}

	public Optional<Proyecto> porSlug(String slug) {
		return PROYECTOS.stream().filter(p -> p.slug().equals(slug)).findFirst();
	}

}

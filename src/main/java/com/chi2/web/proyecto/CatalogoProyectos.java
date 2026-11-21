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
			// Proyectos en desarrollo (repos chi2-peluqueria y chi2-gestor_tareas). Textos: docs/02-contenido.md §4.
			new Proyecto(
					"barberia",
					"Sistema para barberías",
					"Barberías y peluquerías",
					EstadoProyecto.EN_DESARROLLO,
					"Turnos online, la agenda de cada barbero y el control de lo que se cobra, todo desde el celular.",
					"Las barberías arreglan los turnos por WhatsApp, a mano: el dueño o los barberos contestan mensajes y anotan en un cuaderno o en el celular. Se pisan turnos, se pierden mensajes y nadie sabe con certeza cuántos cortes hizo cada barbero ni cuánto se cobró en efectivo y cuánto por transferencia.",
					"Cada barbería tiene su link de reservas: el cliente elige el servicio, el barbero (o \"cualquiera\"), el día y un horario libre, y la reserva le llega a la barbería por WhatsApp, sin costo. Cada barbero ve su agenda y marca cada turno como empezado y terminado; el dueño ve la de todos. Al terminar se anota cuánto se cobró y cómo, y cada uno ve sus números del día, la semana y el mes. Está pensado para usarse desde el celular, sin instalar nada desde una tienda de apps.",
					List.of("Java", "Spring Boot", "PostgreSQL", "Tailwind CSS"),
					"Ya funcionan el alta de cada barbería con sus usuarios, el catálogo de servicios, el perfil y los horarios de cada barbero (con francos y ausencias) y la agenda de turnos sin superposiciones. Siguen la atención de cada turno con el registro del cobro, las métricas por barbero y del negocio, y la reserva online con aviso por WhatsApp."),
			new Proyecto(
					"gestion-de-tareas",
					"Gestión de tareas",
					"Organización personal y de equipos",
					EstadoProyecto.EN_DESARROLLO,
					"Lo de hoy, la semana, el mes y el largo plazo en un solo lugar, con prioridades y tablero.",
					"Las tareas quedan repartidas entre notas del celular, papelitos, chats y la memoria. Cuesta ver qué hay que hacer hoy, qué se viene en la semana y qué quedó sin planificar, y lo urgente se mezcla con lo que puede esperar. Las herramientas conocidas tienen tantas opciones que muchas veces terminan sin usarse.",
					"Al abrirlo muestra lo de hoy, ordenado por prioridad y con lo atrasado a la vista. Una tarea se carga en segundos: se escribe y se guarda con Enter, con atajos para la prioridad y el día. La semana y el mes se ven como calendario, lista o tablero, donde las tareas se arrastran de pendiente a en curso y a finalizada; lo que todavía no tiene fecha queda en \"Por planificar\". Todo se guarda al instante, sin botón de guardar.",
					List.of("Java", "Spring Boot", "PostgreSQL", "JavaScript", "Tailwind CSS"),
					"La primera etapa, de uso personal, ya funciona: vistas de hoy, semana, mes y largo plazo, carga rápida, tablero y edición al instante. Sigue la etapa de grupos, con tareas compartidas y roles, que se va a probar primero con un equipo de tesis de la facultad. Después, adaptarlo como producto para negocios."));

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

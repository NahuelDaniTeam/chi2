package com.chi2.web.proyecto;

import com.chi2.web.WebMvcTestConfig;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.not;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@WebMvcTest(ProyectoController.class)
@Import(WebMvcTestConfig.class)
class ProyectoControllerTest {

	@Autowired
	MockMvc mvc;

	@Test
	void listaSeparaTrabajosAnterioresDeProyectosEnDesarrollo() throws Exception {
		mvc.perform(get("/proyectos"))
				.andExpect(status().isOk())
				.andExpect(view().name("proyectos/lista"))
				.andExpect(content().string(allOf(
						containsString("Trabajos anteriores"),
						containsString("Seguimiento de camiones recolectores"),
						containsString("Cálculo de medidas para una imprenta"),
						containsString("En desarrollo"),
						containsString("Sistema para barberías"),
						containsString("Gestión de tareas"))));
	}

	@Test
	void detalleDeUnCasoMuestraElResultado() throws Exception {
		mvc.perform(get("/proyectos/imprenta"))
				.andExpect(status().isOk())
				.andExpect(content().string(allOf(
						containsString("El problema"),
						containsString("La solución"),
						containsString("Tecnologías"),
						containsString("Resultado"))));
	}

	@Test
	void detalleDeUnProyectoEnDesarrolloNoMuestraResultado() throws Exception {
		mvc.perform(get("/proyectos/barberia"))
				.andExpect(status().isOk())
				.andExpect(view().name("proyectos/detalle"))
				.andExpect(content().string(allOf(
						containsString("En desarrollo"),
						not(containsString(">Resultado<")))));
	}

	@Test
	void losProyectosEnDesarrolloMuestranSuEstadoActualSinTextosPendientes() throws Exception {
		for (String slug : new String[] {"barberia", "gestion-de-tareas"}) {
			mvc.perform(get("/proyectos/" + slug))
					.andExpect(status().isOk())
					.andExpect(content().string(allOf(
							containsString("El problema"),
							containsString("La solución"),
							containsString("Estado actual"),
							not(containsString("[PENDIENTE")))));
		}
	}

	@Test
	void proyectoInexistenteDa404() throws Exception {
		mvc.perform(get("/proyectos/no-existe")).andExpect(status().isNotFound());
	}

}

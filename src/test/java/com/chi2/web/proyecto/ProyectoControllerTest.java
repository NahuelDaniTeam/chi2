package com.chi2.web.proyecto;

import com.chi2.web.WebMvcTestConfig;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.containsString;
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
	void listaMuestraLosDosProyectos() throws Exception {
		mvc.perform(get("/proyectos"))
				.andExpect(status().isOk())
				.andExpect(view().name("proyectos/lista"))
				.andExpect(content().string(allOf(
						containsString("Sistema para barberías"),
						containsString("Gestión de tareas"))));
	}

	@Test
	void detalleMuestraProblemaSolucionTecnologiasYEstado() throws Exception {
		mvc.perform(get("/proyectos/barberia"))
				.andExpect(status().isOk())
				.andExpect(view().name("proyectos/detalle"))
				.andExpect(content().string(allOf(
						containsString("El problema"),
						containsString("La solución"),
						containsString("Tecnologías"),
						containsString("En desarrollo"))));
	}

	@Test
	void proyectoInexistenteDa404() throws Exception {
		mvc.perform(get("/proyectos/no-existe")).andExpect(status().isNotFound());
	}

}

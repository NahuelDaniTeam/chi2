package com.chi2.web.pagina;

import com.chi2.web.WebMvcTestConfig;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@WebMvcTest(PaginaController.class)
@Import(WebMvcTestConfig.class)
@TestPropertySource(properties = "chi2.contacto.whatsapp=5493510000000")
class PaginaControllerTest {

	@Autowired
	MockMvc mvc;

	@ParameterizedTest
	@CsvSource({
			"/, inicio",
			"/servicios, servicios",
			"/quienes-somos, quienes-somos"
	})
	void cadaPaginaMuestraWhatsappYElTiempoDeRespuesta(String ruta, String vista) throws Exception {
		mvc.perform(get(ruta))
				.andExpect(status().isOk())
				.andExpect(view().name(vista))
				.andExpect(content().string(containsString("https://wa.me/5493510000000")));
	}

	@Test
	void inicioMuestraTitularServiciosYProyectos() throws Exception {
		mvc.perform(get("/"))
				.andExpect(content().string(allOf(
						containsString("Entendemos tu negocio y construimos la herramienta"),
						containsString("que necesita."),
						containsString("Sistemas a medida"),
						containsString("Análisis de tu negocio"),
						containsString("Respondemos en 24 horas hábiles."),
						containsString("/proyectos/camiones-recolectores"),
						containsString("/proyectos/imprenta"),
						containsString("/proyectos/barberia"),
						containsString("/proyectos/gestion-de-tareas"))));
	}

	@Test
	void quienesSomosMuestraLasFotosDelEquipo() throws Exception {
		mvc.perform(get("/quienes-somos"))
				.andExpect(content().string(allOf(
						containsString("/img/equipo/nahuel.webp"),
						containsString("/img/equipo/daniel.webp"),
						containsString("alt=\"Nahuel Luna\""),
						containsString("alt=\"Daniel Patolsky\""))));
	}

	@Test
	void serviciosMuestraLosCincoPasosDeComoTrabajamos() throws Exception {
		mvc.perform(get("/servicios"))
				.andExpect(content().string(allOf(
						containsString("Cómo trabajamos"),
						containsString("La primera charla es gratis."),
						containsString("soporte después de la entrega"))));
	}

}

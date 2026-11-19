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
			"/quienes-somos, quienes-somos",
			"/contacto, contacto"
	})
	void cadaPaginaMuestraWhatsappYElTiempoDeRespuesta(String ruta, String vista) throws Exception {
		mvc.perform(get(ruta))
				.andExpect(status().isOk())
				.andExpect(view().name(vista))
				.andExpect(content().string(containsString("https://wa.me/5493510000000")));
	}

	@Test
	void inicioMuestraLosProyectosEnDesarrollo() throws Exception {
		mvc.perform(get("/"))
				.andExpect(content().string(allOf(
						containsString("Respondemos en 24 horas hábiles."),
						containsString("/proyectos/barberia"),
						containsString("/proyectos/gestion-de-tareas"),
						containsString("En desarrollo"))));
	}

}

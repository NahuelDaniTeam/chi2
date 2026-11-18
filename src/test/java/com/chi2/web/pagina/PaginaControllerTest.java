package com.chi2.web.pagina;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@WebMvcTest(PaginaController.class)
class PaginaControllerTest {

	@Autowired
	MockMvc mvc;

	@ParameterizedTest
	@CsvSource({
			"/, inicio",
			"/servicios, servicios",
			"/casos, casos",
			"/quienes-somos, quienes-somos",
			"/contacto, contacto"
	})
	void cadaPaginaRenderizaSuVista(String ruta, String vista) throws Exception {
		mvc.perform(get(ruta))
				.andExpect(status().isOk())
				.andExpect(view().name(vista))
				.andExpect(content().string(containsString("CHI2")));
	}

}

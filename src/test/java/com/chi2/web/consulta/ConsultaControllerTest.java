package com.chi2.web.consulta;

import com.chi2.web.WebMvcTestConfig;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.not;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.flash;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

/** Formulario web {@code /contacto} (§5.1). Criterios 1, 4, 9, 10 y 12 del lado web. */
@WebMvcTest(ConsultaController.class)
@Import(WebMvcTestConfig.class)
class ConsultaControllerTest {

	@Autowired
	MockMvc mvc;

	@MockitoBean
	ConsultaService service;

	@Test
	void laPaginaMuestraUnFormularioHtmlComunQueFuncionaSinJavascript() throws Exception {
		mvc.perform(get("/contacto"))
				.andExpect(status().isOk())
				.andExpect(view().name("contacto"))
				.andExpect(content().string(allOf(
						containsString("method=\"post\""),
						containsString("action=\"/contacto\""),
						containsString("name=\"nombre\""),
						containsString("name=\"contacto\""),
						containsString("name=\"rubro\""),
						containsString("name=\"mensaje\""),
						containsString("name=\"sitio_web\""),
						containsString("type=\"submit\""),
						containsString("los borramos a los 3 meses"))));
	}

	@Test
	void conDatosValidosRegistraYRedirigeAlMensajeDeExito() throws Exception {
		mvc.perform(post("/contacto")
						.param("nombre", "Ana")
						.param("contacto", "351 555-1234")
						.param("rubro", "Barbería")
						.param("mensaje", "Quiero ordenar los turnos"))
				.andExpect(redirectedUrl("/contacto?enviada"))
				.andExpect(flash().attribute("nombreEnviado", "Ana"));

		verify(service).registrar(any(ConsultaForm.class), eq("127.0.0.1"));
	}

	@Test
	void laPaginaDeExitoSaludaPorElNombreYNoMuestraElFormulario() throws Exception {
		mvc.perform(get("/contacto").param("enviada", "").flashAttr("nombreEnviado", "Ana"))
				.andExpect(status().isOk())
				.andExpect(content().string(allOf(
						containsString("¡Listo, Ana!"),
						containsString("Recibimos tu consulta. Respondemos en 24 horas hábiles."),
						not(containsString("name=\"mensaje\"")))));
	}

	@Test
	void conErroresNoRegistraYVuelveConLosErroresYLoEscrito() throws Exception {
		mvc.perform(post("/contacto")
						.param("nombre", "Ana")
						.param("contacto", "hola")
						.param("mensaje", "corto"))
				.andExpect(status().isOk())
				.andExpect(view().name("contacto"))
				.andExpect(model().attributeHasFieldErrors("consulta", "contacto", "mensaje"))
				.andExpect(model().attributeHasNoErrors("contacto"))
				.andExpect(model().attribute("primerCampoConError", "contacto"))
				.andExpect(content().string(allOf(
						containsString("Dejanos un WhatsApp o un mail válido para responderte."),
						containsString("Contanos un poco más (mínimo 10 caracteres)."),
						containsString("value=\"Ana\""),
						containsString("value=\"hola\""),
						containsString("aria-describedby=\"contacto-error\""))));

		verifyNoInteractions(service);
	}

	@Test
	void sinNombreMuestraSuMensaje() throws Exception {
		mvc.perform(post("/contacto")
						.param("nombre", "   ")
						.param("contacto", "ana@mail.com")
						.param("mensaje", "Quiero ordenar los turnos"))
				.andExpect(model().attributeHasFieldErrors("consulta", "nombre"))
				.andExpect(content().string(containsString("Contanos cómo te llamás.")));
	}

	@Test
	void conElCampoTrampaLlenoRespondeExitoAunqueHayaErrores() throws Exception {
		mvc.perform(post("/contacto")
						.param("nombre", "x")
						.param("sitio_web", "http://spam"))
				.andExpect(redirectedUrl("/contacto?enviada"));

		verify(service).registrar(any(ConsultaForm.class), any());
	}

	@Test
	void siSeSuperaElLimiteRespondeCon429YConservaLoEscrito() throws Exception {
		doThrow(new LimiteDeConsultasExcedidoException()).when(service).registrar(any(), any());

		mvc.perform(post("/contacto")
						.param("nombre", "Ana")
						.param("contacto", "ana@mail.com")
						.param("mensaje", "Quiero ordenar los turnos"))
				.andExpect(status().isTooManyRequests())
				.andExpect(view().name("contacto"))
				.andExpect(content().string(allOf(
						containsString("Recibimos muchas consultas desde tu conexión."),
						containsString("value=\"ana@mail.com\""))));
	}

}

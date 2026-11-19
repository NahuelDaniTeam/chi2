package com.chi2.web.api;

import com.chi2.web.WebMvcTestConfig;
import com.chi2.web.consulta.ConsultaRequest;
import com.chi2.web.consulta.ConsultaService;
import com.chi2.web.consulta.LimiteDeConsultasExcedidoException;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.aMapWithSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/** {@code POST /api/consultas} (§5.2). Criterios 2, 4, 9 y 10 del lado de la API. */
@WebMvcTest(ConsultaApiController.class)
@Import(WebMvcTestConfig.class)
class ConsultaApiControllerTest {

	@Autowired
	MockMvc mvc;

	@MockitoBean
	ConsultaService service;

	@Test
	void conDatosValidosResponde201SinDevolverDatosGuardados() throws Exception {
		mvc.perform(post("/api/consultas").contentType(MediaType.APPLICATION_JSON).content("""
						{ "nombre": "  Ana ", "contacto": "351 555-1234", "rubro": "Barbería",
						  "mensaje": "Quiero ordenar los turnos..." }
						"""))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.mensaje").value("Recibimos tu consulta. Respondemos en 24 horas hábiles."))
				.andExpect(jsonPath("$.id").doesNotExist());

		verify(service).registrar(argThat((ConsultaRequest r) -> r.nombre().equals("Ana")), any());
	}

	@Test
	void conErroresResponde400ConElErrorDeCadaCampo() throws Exception {
		mvc.perform(post("/api/consultas").contentType(MediaType.APPLICATION_JSON).content("""
						{ "nombre": "A", "contacto": "ana@", "mensaje": "corto" }
						"""))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.status").value(400))
				.andExpect(jsonPath("$.errores", aMapWithSize(3)))
				.andExpect(jsonPath("$.errores.nombre").value("Contanos cómo te llamás."))
				.andExpect(jsonPath("$.errores.contacto").value("Dejanos un WhatsApp o un mail válido para responderte."))
				.andExpect(jsonPath("$.errores.mensaje").value("Contanos un poco más (mínimo 10 caracteres)."));

		verifyNoInteractions(service);
	}

	@Test
	void conJsonMalformadoResponde400SinDetallesInternos() throws Exception {
		mvc.perform(post("/api/consultas").contentType(MediaType.APPLICATION_JSON).content("{ \"nombre\": "))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.detail").value("No pudimos leer el pedido. Mandá un JSON válido en UTF-8."))
				.andExpect(jsonPath("$.trace").doesNotExist());

		verifyNoInteractions(service);
	}

	@Test
	void conElCampoTrampaLlenoResponde201() throws Exception {
		mvc.perform(post("/api/consultas").contentType(MediaType.APPLICATION_JSON).content("""
						{ "nombre": "x", "sitio_web": "http://spam" }
						"""))
				.andExpect(status().isCreated());

		verify(service).registrar(argThat(ConsultaRequest::esTrampa), any());
	}

	@Test
	void siSeSuperaElLimiteResponde429() throws Exception {
		doThrow(new LimiteDeConsultasExcedidoException()).when(service).registrar(any(), any());

		mvc.perform(post("/api/consultas").contentType(MediaType.APPLICATION_JSON).content("""
						{ "nombre": "Ana", "contacto": "ana@mail.com", "mensaje": "Quiero ordenar los turnos" }
						"""))
				.andExpect(status().isTooManyRequests())
				.andExpect(jsonPath("$.detail").value(LimiteDeConsultasExcedidoException.MENSAJE));
	}

}

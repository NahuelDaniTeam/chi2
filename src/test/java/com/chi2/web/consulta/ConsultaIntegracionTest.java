package com.chi2.web.consulta;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

import com.chi2.web.TestcontainersConfiguration;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.mail.MailSendException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Flujo completo contra PostgreSQL real (Testcontainers), con el envío de mail simulado.
 * Criterios 1, 2, 3, 6, 7 y 11.
 */
@SpringBootTest
@AutoConfigureMockMvc
@Import(TestcontainersConfiguration.class)
@TestPropertySource(properties = {
		"chi2.consultas.destinatarios=nahuel@test.ar, daniel@test.ar",
		"chi2.consultas.limite-por-hora=1000"
})
class ConsultaIntegracionTest {

	@Autowired
	MockMvc mvc;

	@Autowired
	ConsultaRepository repository;

	@Autowired
	ReintentoAvisosService reintento;

	@Autowired
	RetencionConsultasService retencion;

	@MockitoBean
	JavaMailSender mailSender;

	@BeforeEach
	void limpiar() {
		repository.deleteAll();
		reset(mailSender);
	}

	@Test
	void elFormularioWebGuardaLaConsultaYAvisaALosDos() throws Exception {
		mvc.perform(post("/contacto")
						.param("nombre", "Ana")
						.param("contacto", "ana@mail.com")
						.param("rubro", "Barbería")
						.param("mensaje", "Quiero ordenar los turnos"))
				.andExpect(redirectedUrl("/contacto?enviada"));

		Consulta guardada = unicaConsulta();
		assertThat(guardada.getNombre()).isEqualTo("Ana");
		assertThat(guardada.getTipoContacto()).isEqualTo(TipoContacto.MAIL);
		assertThat(guardada.getEstadoAviso()).isEqualTo(EstadoAviso.ENVIADO);
		assertThat(guardada.getAvisadaEn()).isNotNull();

		ArgumentCaptor<SimpleMailMessage> mail = ArgumentCaptor.forClass(SimpleMailMessage.class);
		verify(mailSender).send(mail.capture());
		assertThat(mail.getValue().getTo()).containsExactly("nahuel@test.ar", "daniel@test.ar");
		assertThat(mail.getValue().getReplyTo()).isEqualTo("ana@mail.com");
		assertThat(mail.getValue().getSubject()).isEqualTo("Nueva consulta: Ana (Barbería)");
	}

	@Test
	void laApiGuardaLaConsulta() throws Exception {
		mvc.perform(post("/api/consultas").contentType(MediaType.APPLICATION_JSON).content("""
						{ "nombre": "Juan", "contacto": "+54 9 351 555-1234", "mensaje": "Necesito un sistema de tareas" }
						"""))
				.andExpect(status().isCreated());

		Consulta guardada = unicaConsulta();
		assertThat(guardada.getTipoContacto()).isEqualTo(TipoContacto.WHATSAPP);
		assertThat(guardada.getContacto()).isEqualTo("+54 9 351 555-1234");
		assertThat(guardada.getRubro()).isNull();
	}

	@Test
	void siElMailFallaLaConsultaQuedaGuardadaYElReintentoLaAvisaDespues() throws Exception {
		doThrow(new MailSendException("SMTP caído")).when(mailSender).send(any(SimpleMailMessage.class));

		mvc.perform(post("/contacto")
						.param("nombre", "Ana")
						.param("contacto", "ana@mail.com")
						.param("mensaje", "Quiero ordenar los turnos"))
				.andExpect(redirectedUrl("/contacto?enviada"));

		Consulta pendiente = unicaConsulta();
		assertThat(pendiente.getEstadoAviso()).isEqualTo(EstadoAviso.PENDIENTE);
		assertThat(pendiente.getIntentosAviso()).isEqualTo(1);
		assertThat(pendiente.getUltimoError()).isEqualTo("SMTP caído");

		doNothing().when(mailSender).send(any(SimpleMailMessage.class));
		reintento.reintentarPendientes();

		Consulta avisada = unicaConsulta();
		assertThat(avisada.getEstadoAviso()).isEqualTo(EstadoAviso.ENVIADO);
		assertThat(avisada.getUltimoError()).isNull();
	}

	@Test
	void laRetencionBorraLasDeMasDeTresMesesYDejaLasNuevas() {
		Instant ahora = Instant.now();
		repository.save(consultaCreadaEn(ahora.minus(100, ChronoUnit.DAYS)));
		repository.save(consultaCreadaEn(ahora.minus(80, ChronoUnit.DAYS)));
		repository.save(consultaCreadaEn(ahora));

		int borradas = retencion.borrarVencidas();

		assertThat(borradas).isEqualTo(1);
		assertThat(repository.count()).isEqualTo(2);
	}

	private Consulta unicaConsulta() {
		assertThat(repository.count()).isEqualTo(1);
		return repository.findAll().getFirst();
	}

	private static Consulta consultaCreadaEn(Instant creadaEn) {
		return new Consulta("Ana", "ana@mail.com", TipoContacto.MAIL, null, "Quiero ordenar los turnos", creadaEn);
	}

}

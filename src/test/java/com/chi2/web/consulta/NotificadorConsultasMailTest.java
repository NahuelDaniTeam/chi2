package com.chi2.web.consulta;

import java.time.Instant;
import java.time.Period;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/** Criterio 3: destinatarios, asunto, cuerpo y Reply-To del aviso (§7). */
class NotificadorConsultasMailTest {

	private static final Instant CREADA = Instant.parse("2026-11-18T18:42:00Z"); // 15:42 en Argentina

	private final JavaMailSender mailSender = mock(JavaMailSender.class);
	private final ConsultasProperties properties = new ConsultasProperties(
			List.of("nahuel@test.ar", "daniel@test.ar"), "chi2@test.ar", 5, Period.ofMonths(3));
	private final NotificadorConsultasMail notificador =
			new NotificadorConsultasMail(mailSender, new MensajeAvisoConsulta(), properties);

	@Test
	void avisaPorWhatsappConLinkDeRespuestaYSinReplyTo() {
		var consulta = new Consulta("Ana", "351 555-1234", TipoContacto.WHATSAPP, "Barbería",
				"Quiero ordenar los turnos...", CREADA);

		SimpleMailMessage mail = enviar(consulta);

		assertThat(mail.getFrom()).isEqualTo("chi2@test.ar");
		assertThat(mail.getTo()).containsExactly("nahuel@test.ar", "daniel@test.ar");
		assertThat(mail.getReplyTo()).isNull();
		assertThat(mail.getSubject()).isEqualTo("Nueva consulta: Ana (Barbería)");
		assertThat(mail.getText()).isEqualTo("""
				Nueva consulta desde el sitio.

				Nombre:   Ana
				Contacto: 351 555-1234 (WhatsApp)
				Rubro:    Barbería
				Fecha:    18/11/2026 15:42 (hora Argentina)

				Mensaje:
				Quiero ordenar los turnos...

				Responder por WhatsApp: https://wa.me/5493515551234
				""");
	}

	@Test
	void avisaPorMailConReplyToYSinRubroNiLinkDeWhatsapp() {
		var consulta = new Consulta("Ana", "ana@mail.com", TipoContacto.MAIL, null, "Quiero ordenar los turnos...",
				CREADA);

		SimpleMailMessage mail = enviar(consulta);

		assertThat(mail.getReplyTo()).isEqualTo("ana@mail.com");
		assertThat(mail.getSubject()).isEqualTo("Nueva consulta: Ana");
		assertThat(mail.getText())
				.contains("Contacto: ana@mail.com (mail)")
				.contains("Rubro:    -")
				.doesNotContain("wa.me");
	}

	@Test
	void sinDestinatariosNoEstaConfigurado() {
		var sinDestinatarios = new ConsultasProperties(List.of(" "), null, 5, Period.ofMonths(3));
		assertThat(new NotificadorConsultasMail(mailSender, new MensajeAvisoConsulta(), sinDestinatarios)
				.estaConfigurado()).isFalse();
		assertThat(notificador.estaConfigurado()).isTrue();
	}

	private SimpleMailMessage enviar(Consulta consulta) {
		notificador.avisar(consulta);
		ArgumentCaptor<SimpleMailMessage> captor = ArgumentCaptor.forClass(SimpleMailMessage.class);
		verify(mailSender).send(captor.capture());
		return captor.getValue();
	}

}

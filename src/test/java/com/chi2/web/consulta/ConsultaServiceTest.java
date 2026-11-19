package com.chi2.web.consulta;

import java.time.Instant;
import java.time.Period;
import java.util.List;

import com.chi2.web.RelojDeTest;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.system.CapturedOutput;
import org.springframework.boot.test.system.OutputCaptureExtension;
import org.springframework.mail.MailSendException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

/** Criterios 6, 8 y 9: registro, aviso que falla, intentos agotados y campo trampa. */
@ExtendWith(OutputCaptureExtension.class)
class ConsultaServiceTest {

	private static final Instant AHORA = Instant.parse("2026-11-18T12:00:00Z");

	private final RelojDeTest reloj = new RelojDeTest(AHORA);
	private final ConsultaRepository repository = mock(ConsultaRepository.class);
	private final NotificadorConsultas notificador = mock(NotificadorConsultas.class);
	private final AvisoConsultas avisos = new AvisoConsultas(notificador, repository, reloj);
	private final ConsultaService service = new ConsultaService(repository, new ConsultaMapper(),
			new LimitadorConsultas(new ConsultasProperties(List.of(), null, 5, Period.ofMonths(3)), reloj),
			avisos, reloj);

	ConsultaServiceTest() {
		when(repository.save(any(Consulta.class))).thenAnswer(invocacion -> invocacion.getArgument(0));
		when(notificador.estaConfigurado()).thenReturn(true);
	}

	@Test
	void guardaLaConsultaYLaMarcaAvisada() {
		Consulta consulta = registrarValida();

		assertThat(consulta.getEstadoAviso()).isEqualTo(EstadoAviso.ENVIADO);
		assertThat(consulta.getAvisadaEn()).isEqualTo(AHORA);
		assertThat(consulta.getCreadaEn()).isEqualTo(AHORA);
		assertThat(consulta.getTipoContacto()).isEqualTo(TipoContacto.WHATSAPP);
		assertThat(consulta.getContacto()).isEqualTo("351 555-1234");
	}

	@Test
	void siElMailFallaLaConsultaQuedaPendienteConUnIntento() {
		doThrow(new MailSendException("SMTP caído")).when(notificador).avisar(any());

		Consulta consulta = registrarValida();

		assertThat(consulta.getEstadoAviso()).isEqualTo(EstadoAviso.PENDIENTE);
		assertThat(consulta.getIntentosAviso()).isEqualTo(1);
		assertThat(consulta.getUltimoError()).isEqualTo("SMTP caído");
	}

	@Test
	void sinDestinatariosQuedaPendienteSinGastarIntentos(CapturedOutput salida) {
		when(notificador.estaConfigurado()).thenReturn(false);

		Consulta consulta = registrarValida();

		assertThat(consulta.getEstadoAviso()).isEqualTo(EstadoAviso.PENDIENTE);
		assertThat(consulta.getIntentosAviso()).isZero();
		verify(notificador, never()).avisar(any());
		assertThat(salida).contains("WARN").contains("no hay destinatarios configurados");
	}

	@Test
	void alDecimoIntentoFallidoPasaAFallidoYDejaUnLogDeError(CapturedOutput salida) {
		doThrow(new MailSendException("SMTP caído")).when(notificador).avisar(any());
		Consulta consulta = registrarValida();

		for (int intento = 2; intento <= Consulta.MAXIMO_INTENTOS_AVISO; intento++) {
			avisos.intentar(consulta);
		}

		assertThat(consulta.getIntentosAviso()).isEqualTo(10);
		assertThat(consulta.getEstadoAviso()).isEqualTo(EstadoAviso.FALLIDO);
		assertThat(salida).contains("ERROR").contains("marcada FALLIDO tras 10 intentos");
	}

	@Test
	void conElCampoTrampaLlenoNoGuardaNiAvisa() {
		service.registrar(new ConsultaForm("Bot", "bot@spam.com", null, "Compre seguidores baratos", "http://spam"),
				"1.1.1.1");

		verifyNoInteractions(repository, notificador);
	}

	private Consulta registrarValida() {
		service.registrar(new ConsultaForm("Ana", "351 555-1234", "Barbería", "Quiero ordenar los turnos", null),
				"1.1.1.1");
		var captor = org.mockito.ArgumentCaptor.forClass(Consulta.class);
		verify(repository, org.mockito.Mockito.atLeastOnce()).save(captor.capture());
		return captor.getValue();
	}

}

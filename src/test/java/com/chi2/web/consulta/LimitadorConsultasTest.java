package com.chi2.web.consulta;

import java.time.Duration;
import java.time.Instant;
import java.time.Period;
import java.util.List;

import com.chi2.web.RelojDeTest;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/** Criterio 10: 5 envíos por hora por IP (§6). */
class LimitadorConsultasTest {

	private final RelojDeTest reloj = new RelojDeTest(Instant.parse("2026-11-18T12:00:00Z"));
	private final LimitadorConsultas limitador = new LimitadorConsultas(
			new ConsultasProperties(List.of(), null, 5, Period.ofMonths(3)), reloj);

	@Test
	void elSextoEnvioDeLaMismaIpDentroDeUnaHoraSeRechaza() {
		enviarCinco("1.1.1.1");

		assertThatThrownBy(() -> limitador.registrarEnvio("1.1.1.1"))
				.isInstanceOf(LimiteDeConsultasExcedidoException.class);
	}

	@Test
	void otraIpNoSeVeAfectada() {
		enviarCinco("1.1.1.1");

		assertThatCode(() -> limitador.registrarEnvio("2.2.2.2")).doesNotThrowAnyException();
	}

	@Test
	void pasadaUnaHoraSePuedeVolverAEnviar() {
		enviarCinco("1.1.1.1");
		reloj.adelantar(Duration.ofMinutes(61));

		assertThatCode(() -> limitador.registrarEnvio("1.1.1.1")).doesNotThrowAnyException();
	}

	@Test
	void laLimpiezaNoAfectaLosEnviosRecientes() {
		enviarCinco("1.1.1.1");
		limitador.limpiar();

		assertThatThrownBy(() -> limitador.registrarEnvio("1.1.1.1"))
				.isInstanceOf(LimiteDeConsultasExcedidoException.class);
	}

	private void enviarCinco(String ip) {
		for (int i = 0; i < 5; i++) {
			limitador.registrarEnvio(ip);
		}
	}

}

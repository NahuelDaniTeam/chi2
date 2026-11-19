package com.chi2.web.consulta;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;

/** Criterio 5: qué acepta y qué rechaza "WhatsApp o mail" (§3.1). */
class TipoContactoTest {

	@ParameterizedTest
	@CsvSource({
			"ana@mail.com, MAIL",
			"'+54 9 351 555-1234', WHATSAPP",
			"3515551234, WHATSAPP",
			"(0351) 555-1234, WHATSAPP"
	})
	void aceptaMailsYTelefonos(String contacto, TipoContacto esperado) {
		assertThat(TipoContacto.detectar(contacto)).contains(esperado);
	}

	@ParameterizedTest
	@NullAndEmptySource
	@ValueSource(strings = { "hola", "123", "ana@", "ana@mail", "1234567890123456" })
	void rechazaLoQueNoEsNiMailNiTelefono(String contacto) {
		assertThat(TipoContacto.detectar(contacto)).isEmpty();
	}

	@Test
	void rechazaContactosDeMasDe120Caracteres() {
		assertThat(TipoContacto.detectar("a".repeat(110) + "@mail.com.ar")).isEmpty();
	}

	@ParameterizedTest
	@CsvSource({
			"'351 555-1234', 5493515551234",
			"'+54 9 351 555-1234', 5493515551234",
			"5493515551234, 5493515551234"
	})
	void armaElNumeroParaWaMe(String contacto, String esperado) {
		assertThat(TipoContacto.numeroWhatsapp(contacto)).isEqualTo(esperado);
	}

}

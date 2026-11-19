package com.chi2.web.contacto;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ContactoPropertiesTest {

	@Test
	void armaElLinkDeWhatsappConElMensajeCodificado() {
		var contacto = new ContactoProperties("5493510000000", null, "Hola CHI2, quiero hacer una consulta.");
		assertThat(contacto.linkWhatsapp())
				.isEqualTo("https://wa.me/5493510000000?text=Hola+CHI2%2C+quiero+hacer+una+consulta.");
	}

	@Test
	void sinNumeroCaeEnLaPaginaDeContacto() {
		assertThat(new ContactoProperties("", null, "x").linkWhatsapp()).isEqualTo("/contacto");
		assertThat(new ContactoProperties(null, null, null).linkWhatsapp()).isEqualTo("/contacto");
	}

}

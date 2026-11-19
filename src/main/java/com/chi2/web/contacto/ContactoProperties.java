package com.chi2.web.contacto;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Datos de contacto públicos (CLAUDE.md §7). Se configuran en application.properties
 * o por variables de entorno (CHI2_CONTACTO_WHATSAPP, CHI2_CONTACTO_EMAIL).
 *
 * @param whatsapp número en formato internacional sin "+" ni espacios (ej: 5493511234567)
 * @param email    mail de contacto
 * @param mensajeWhatsapp texto precargado al abrir el chat
 */
@ConfigurationProperties("chi2.contacto")
public record ContactoProperties(String whatsapp, String email, String mensajeWhatsapp) {

	/** Compromiso de respuesta de la v1 (docs/01-vision-alcance.md §6). */
	public static final String TIEMPO_RESPUESTA = "Respondemos en 24 horas hábiles.";

	public boolean tieneWhatsapp() {
		return whatsapp != null && !whatsapp.isBlank();
	}

	/** Link wa.me listo para usar; si todavía no hay número, cae en la página de contacto. */
	public String linkWhatsapp() {
		if (!tieneWhatsapp()) {
			return "/contacto";
		}
		String texto = mensajeWhatsapp == null ? "" : mensajeWhatsapp;
		return "https://wa.me/" + whatsapp + "?text=" + URLEncoder.encode(texto, StandardCharsets.UTF_8);
	}

	public String tiempoRespuesta() {
		return TIEMPO_RESPUESTA;
	}

}

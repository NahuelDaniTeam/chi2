package com.chi2.web.consulta;

import java.time.Period;
import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.DefaultValue;

/**
 * Configuración del formulario de contacto (§9.2).
 *
 * @param destinatarios mails que reciben el aviso; vacío = no se avisa y la consulta queda pendiente
 * @param remitente     dirección "De" del aviso (la cuenta Gmail en producción)
 * @param limitePorHora consultas permitidas por IP por hora (§6)
 * @param retencion     cuánto se guardan las consultas antes de borrarlas (§4.2)
 */
@ConfigurationProperties("chi2.consultas")
public record ConsultasProperties(
		@DefaultValue List<String> destinatarios,
		@DefaultValue(REMITENTE_POR_DEFECTO) String remitente,
		@DefaultValue("5") int limitePorHora,
		@DefaultValue("P3M") Period retencion) {

	static final String REMITENTE_POR_DEFECTO = "sitio@chi2.local";

	public ConsultasProperties {
		destinatarios = destinatarios == null ? List.of()
				: destinatarios.stream().map(String::trim).filter(d -> !d.isEmpty()).toList();
		if (remitente == null || remitente.isBlank()) {
			remitente = REMITENTE_POR_DEFECTO;
		}
	}

	public boolean hayDestinatarios() {
		return !destinatarios.isEmpty();
	}

}

package com.chi2.web.consulta;

import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import org.springframework.stereotype.Component;

/** Arma el asunto y el cuerpo del aviso (§7). Separado del envío: cambiar el texto no toca el mail. */
@Component
public class MensajeAvisoConsulta {

	private static final ZoneId HORA_ARGENTINA = ZoneId.of("America/Argentina/Cordoba");
	private static final DateTimeFormatter FORMATO_FECHA = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

	public String asunto(Consulta consulta) {
		String asunto = "Nueva consulta: " + consulta.getNombre();
		return consulta.getRubro() == null ? asunto : asunto + " (" + consulta.getRubro() + ")";
	}

	public String cuerpo(Consulta consulta) {
		StringBuilder cuerpo = new StringBuilder()
				.append("Nueva consulta desde el sitio.\n\n")
				.append("Nombre:   ").append(consulta.getNombre()).append('\n')
				.append("Contacto: ").append(consulta.getContacto()).append(" (").append(etiqueta(consulta.getTipoContacto())).append(")\n")
				.append("Rubro:    ").append(consulta.getRubro() == null ? "-" : consulta.getRubro()).append('\n')
				.append("Fecha:    ").append(fechaArgentina(consulta)).append(" (hora Argentina)\n\n")
				.append("Mensaje:\n").append(consulta.getMensaje()).append('\n');
		consulta.linkWhatsapp().ifPresent(link -> cuerpo.append("\nResponder por WhatsApp: ").append(link).append('\n'));
		return cuerpo.toString();
	}

	private static String fechaArgentina(Consulta consulta) {
		return FORMATO_FECHA.format(consulta.getCreadaEn().atZone(HORA_ARGENTINA));
	}

	private static String etiqueta(TipoContacto tipo) {
		return tipo == TipoContacto.WHATSAPP ? "WhatsApp" : "mail";
	}

}

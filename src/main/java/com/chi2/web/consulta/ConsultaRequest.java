package com.chi2.web.consulta;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/** Cuerpo de {@code POST /api/consultas} (§5.2). Reglas iguales a {@link ConsultaForm}. */
public record ConsultaRequest(

		@NotNull(message = MensajesValidacion.NOMBRE)
		@Size(min = 2, max = 80, message = MensajesValidacion.NOMBRE)
		String nombre,

		@ContactoValido
		String contacto,

		@Size(max = 80, message = MensajesValidacion.RUBRO)
		String rubro,

		@NotNull(message = MensajesValidacion.MENSAJE)
		@Size(min = 10, max = 2000, message = MensajesValidacion.MENSAJE)
		String mensaje,

		@JsonProperty("sitio_web")
		String sitioWeb) implements DatosConsulta {

	public ConsultaRequest {
		nombre = DatosConsulta.limpiar(nombre);
		contacto = DatosConsulta.limpiar(contacto);
		rubro = DatosConsulta.limpiar(rubro);
		mensaje = DatosConsulta.limpiar(mensaje);
	}

}

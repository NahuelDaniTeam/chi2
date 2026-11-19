package com.chi2.web.consulta;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import org.springframework.web.bind.annotation.BindParam;

/** Datos del formulario web {@code POST /contacto} (§3). Reglas iguales a {@link ConsultaRequest}. */
public record ConsultaForm(

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

		@BindParam("sitio_web")
		String sitioWeb) implements DatosConsulta {

	public ConsultaForm {
		nombre = DatosConsulta.limpiar(nombre);
		contacto = DatosConsulta.limpiar(contacto);
		rubro = DatosConsulta.limpiar(rubro);
		mensaje = DatosConsulta.limpiar(mensaje);
	}

	public static ConsultaForm vacio() {
		return new ConsultaForm(null, null, null, null, null);
	}

}

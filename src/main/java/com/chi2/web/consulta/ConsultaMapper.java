package com.chi2.web.consulta;

import java.time.Instant;

import org.springframework.stereotype.Component;

/** DTO → entidad. El contacto se guarda tal como lo escribió el visitante (§3.1). */
@Component
public class ConsultaMapper {

	public Consulta aEntidad(DatosConsulta datos, Instant creadaEn) {
		TipoContacto tipo = TipoContacto.detectar(datos.contacto())
				.orElseThrow(() -> new IllegalArgumentException("Contacto inválido: debió fallar la validación"));
		return new Consulta(datos.nombre(), datos.contacto(), tipo, datos.rubro(), datos.mensaje(), creadaEn);
	}

}

package com.chi2.web.consulta;

/** Respuesta de {@code POST /api/consultas}: no devuelve el id ni datos guardados (§5.2). */
public record ConsultaResponse(String mensaje) {

	public static final String RECIBIDA = "Recibimos tu consulta. Respondemos en 24 horas hábiles.";

	public static ConsultaResponse recibida() {
		return new ConsultaResponse(RECIBIDA);
	}

}

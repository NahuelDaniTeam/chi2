package com.chi2.web.consulta;

/** La IP superó las consultas permitidas por hora (§6). Se traduce a HTTP 429. */
public class LimiteDeConsultasExcedidoException extends RuntimeException {

	public static final String MENSAJE =
			"Recibimos muchas consultas desde tu conexión. Probá de nuevo en un rato o escribinos por WhatsApp.";

	public LimiteDeConsultasExcedidoException() {
		super(MENSAJE);
	}

}

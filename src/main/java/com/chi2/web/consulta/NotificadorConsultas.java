package com.chi2.web.consulta;

/**
 * Avisa al equipo que llegó una consulta. El service depende de esta interfaz y no del mail:
 * avisar por otro medio es sumar otra implementación (§9.3).
 */
public interface NotificadorConsultas {

	/** {@code false} si falta configuración (por ejemplo, destinatarios) y no tiene sentido intentar. */
	boolean estaConfigurado();

	/** Manda el aviso. Si falla, lanza una excepción y la consulta queda pendiente de reintento. */
	void avisar(Consulta consulta);

}

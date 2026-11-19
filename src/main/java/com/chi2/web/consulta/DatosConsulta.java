package com.chi2.web.consulta;

/**
 * Lo que manda el visitante, venga del formulario web o de la API. Así el service y el mapper
 * tienen una sola entrada y la regla de negocio no se duplica (DRY).
 */
public interface DatosConsulta {

	String nombre();

	String contacto();

	String rubro();

	String mensaje();

	/** Campo trampa (§6): un humano no lo ve, así que tiene que llegar vacío. */
	String sitioWeb();

	default boolean esTrampa() {
		return sitioWeb() != null && !sitioWeb().isBlank();
	}

	/** Saca espacios de los extremos y convierte el texto vacío en {@code null}. */
	static String limpiar(String texto) {
		if (texto == null) {
			return null;
		}
		String limpio = texto.strip();
		return limpio.isEmpty() ? null : limpio;
	}

}

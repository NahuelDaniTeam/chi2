package com.chi2.web.proyecto;

/** Un proyecto "en desarrollo" pasa a ser un "caso" cuando está terminado. */
public enum EstadoProyecto {

	EN_DESARROLLO("En desarrollo"),
	ACADEMICO("Proyecto académico"),
	CASO("Caso");

	private final String etiqueta;

	EstadoProyecto(String etiqueta) {
		this.etiqueta = etiqueta;
	}

	public String getEtiqueta() {
		return etiqueta;
	}

}

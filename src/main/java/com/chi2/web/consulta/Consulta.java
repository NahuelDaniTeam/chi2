package com.chi2.web.consulta;

import java.time.Instant;
import java.util.Optional;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

/** Consulta recibida por el formulario o la API (§9.1). Nunca sale de la capa de service. */
@Entity
public class Consulta {

	/** Al llegar a esta cantidad de intentos fallidos, se deja de reintentar (§4.1). */
	public static final int MAXIMO_INTENTOS_AVISO = 10;

	private static final int LARGO_MAXIMO_ERROR = 500;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, length = 80)
	private String nombre;

	@Column(nullable = false, length = 120)
	private String contacto;

	@Enumerated(EnumType.STRING)
	@Column(name = "tipo_contacto", nullable = false, length = 10)
	private TipoContacto tipoContacto;

	@Column(length = 80)
	private String rubro;

	@Column(nullable = false, length = 2000)
	private String mensaje;

	@Enumerated(EnumType.STRING)
	@Column(name = "estado_aviso", nullable = false, length = 10)
	private EstadoAviso estadoAviso = EstadoAviso.PENDIENTE;

	@Column(name = "intentos_aviso", nullable = false)
	private int intentosAviso;

	@Column(name = "ultimo_error", length = LARGO_MAXIMO_ERROR)
	private String ultimoError;

	@Column(name = "creada_en", nullable = false)
	private Instant creadaEn;

	@Column(name = "avisada_en")
	private Instant avisadaEn;

	protected Consulta() {
		// para JPA
	}

	public Consulta(String nombre, String contacto, TipoContacto tipoContacto, String rubro, String mensaje,
			Instant creadaEn) {
		this.nombre = nombre;
		this.contacto = contacto;
		this.tipoContacto = tipoContacto;
		this.rubro = rubro;
		this.mensaje = mensaje;
		this.creadaEn = creadaEn;
	}

	public void marcarAvisada(Instant momento) {
		this.estadoAviso = EstadoAviso.ENVIADO;
		this.avisadaEn = momento;
		this.ultimoError = null;
	}

	/**
	 * Registra un intento de aviso fallido.
	 * @return {@code true} si con este intento la consulta pasó a {@link EstadoAviso#FALLIDO}
	 */
	public boolean registrarFalloAviso(String error) {
		this.intentosAviso++;
		this.ultimoError = recortar(error);
		if (intentosAviso >= MAXIMO_INTENTOS_AVISO) {
			this.estadoAviso = EstadoAviso.FALLIDO;
			return true;
		}
		return false;
	}

	/** Link wa.me para responder, solo si dejó un WhatsApp (§7). */
	public Optional<String> linkWhatsapp() {
		return tipoContacto == TipoContacto.WHATSAPP
				? Optional.of("https://wa.me/" + TipoContacto.numeroWhatsapp(contacto))
				: Optional.empty();
	}

	private static String recortar(String error) {
		if (error == null) {
			return "Error desconocido";
		}
		return error.length() <= LARGO_MAXIMO_ERROR ? error : error.substring(0, LARGO_MAXIMO_ERROR);
	}

	public Long getId() {
		return id;
	}

	public String getNombre() {
		return nombre;
	}

	public String getContacto() {
		return contacto;
	}

	public TipoContacto getTipoContacto() {
		return tipoContacto;
	}

	public String getRubro() {
		return rubro;
	}

	public String getMensaje() {
		return mensaje;
	}

	public EstadoAviso getEstadoAviso() {
		return estadoAviso;
	}

	public int getIntentosAviso() {
		return intentosAviso;
	}

	public String getUltimoError() {
		return ultimoError;
	}

	public Instant getCreadaEn() {
		return creadaEn;
	}

	public Instant getAvisadaEn() {
		return avisadaEn;
	}

}

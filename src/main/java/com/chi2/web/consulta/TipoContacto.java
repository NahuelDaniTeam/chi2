package com.chi2.web.consulta;

import java.util.Optional;
import java.util.regex.Pattern;

/** Cómo quiere que le respondan: se deduce de lo que escribió en "WhatsApp o mail" (§3.1). */
public enum TipoContacto {

	MAIL,
	WHATSAPP;

	static final int LARGO_MAXIMO = 120;

	private static final Pattern MAIL_VALIDO = Pattern.compile("^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$");
	private static final Pattern SIMBOLOS_TELEFONO = Pattern.compile("[\\s\\-()+]");
	private static final Pattern TELEFONO_VALIDO = Pattern.compile("^\\d{8,15}$");

	/** Devuelve el tipo si el contacto es válido, o vacío si no es ni mail ni teléfono. */
	public static Optional<TipoContacto> detectar(String contacto) {
		if (contacto == null || contacto.isBlank() || contacto.length() > LARGO_MAXIMO) {
			return Optional.empty();
		}
		if (contacto.contains("@")) {
			return MAIL_VALIDO.matcher(contacto).matches() ? Optional.of(MAIL) : Optional.empty();
		}
		return TELEFONO_VALIDO.matcher(soloDigitos(contacto)).matches() ? Optional.of(WHATSAPP) : Optional.empty();
	}

	/** Número listo para wa.me: sin símbolos y con 549 adelante si no empieza con 54 (§3.1). */
	static String numeroWhatsapp(String contacto) {
		String digitos = soloDigitos(contacto);
		return digitos.startsWith("54") ? digitos : "549" + digitos;
	}

	private static String soloDigitos(String contacto) {
		return SIMBOLOS_TELEFONO.matcher(contacto).replaceAll("");
	}

}

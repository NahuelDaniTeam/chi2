package com.chi2.web.consulta;

import java.time.Clock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Intenta avisar una consulta y deja registrado el resultado. Lo usan el registro y el reintento,
 * así la regla de estados vive en un solo lugar (§4 y §4.1).
 */
@Component
public class AvisoConsultas {

	private static final Logger log = LoggerFactory.getLogger(AvisoConsultas.class);

	private final NotificadorConsultas notificador;
	private final ConsultaRepository repository;
	private final Clock clock;

	public AvisoConsultas(NotificadorConsultas notificador, ConsultaRepository repository, Clock clock) {
		this.notificador = notificador;
		this.repository = repository;
		this.clock = clock;
	}

	public void intentar(Consulta consulta) {
		if (!notificador.estaConfigurado()) {
			log.warn("Consulta {} sin avisar: no hay destinatarios configurados (CHI2_CONSULTAS_DESTINATARIOS)",
					consulta.getId());
			return;
		}
		try {
			notificador.avisar(consulta);
			consulta.marcarAvisada(clock.instant());
		}
		catch (RuntimeException ex) {
			registrarFallo(consulta, ex);
		}
		repository.save(consulta);
	}

	private static void registrarFallo(Consulta consulta, RuntimeException ex) {
		boolean seAgotaronLosIntentos = consulta.registrarFalloAviso(ex.getMessage());
		if (seAgotaronLosIntentos) {
			log.error("Consulta {} marcada FALLIDO tras {} intentos de aviso. Último error: {}",
					consulta.getId(), consulta.getIntentosAviso(), consulta.getUltimoError());
		}
		else {
			log.warn("Falló el aviso de la consulta {} (intento {}): {}",
					consulta.getId(), consulta.getIntentosAviso(), ex.getMessage());
		}
	}

}

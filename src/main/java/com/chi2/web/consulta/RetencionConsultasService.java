package com.chi2.web.consulta;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneOffset;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

/** Todos los días a las 4:00 UTC borra las consultas más viejas que el plazo de retención (§4.2). */
@Service
public class RetencionConsultasService {

	private static final Logger log = LoggerFactory.getLogger(RetencionConsultasService.class);

	private final ConsultaRepository repository;
	private final ConsultasProperties properties;
	private final Clock clock;

	public RetencionConsultasService(ConsultaRepository repository, ConsultasProperties properties, Clock clock) {
		this.repository = repository;
		this.properties = properties;
		this.clock = clock;
	}

	@Scheduled(cron = "0 0 4 * * *", zone = "UTC")
	public int borrarVencidas() {
		Instant limite = clock.instant().atOffset(ZoneOffset.UTC).minus(properties.retencion()).toInstant();
		int borradas = repository.borrarCreadasAntesDe(limite);
		if (borradas > 0) {
			log.info("Retención: se borraron {} consultas creadas antes de {}", borradas, limite);
		}
		return borradas;
	}

}

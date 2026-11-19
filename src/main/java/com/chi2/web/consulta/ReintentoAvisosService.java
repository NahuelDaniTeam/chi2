package com.chi2.web.consulta;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

/** Cada 5 minutos reintenta los avisos pendientes (§4.1). */
@Service
public class ReintentoAvisosService {

	private final ConsultaRepository repository;
	private final AvisoConsultas avisos;

	public ReintentoAvisosService(ConsultaRepository repository, AvisoConsultas avisos) {
		this.repository = repository;
		this.avisos = avisos;
	}

	@Scheduled(fixedDelayString = "PT5M", initialDelayString = "PT1M")
	public void reintentarPendientes() {
		repository.findByEstadoAviso(EstadoAviso.PENDIENTE).forEach(avisos::intentar);
	}

}

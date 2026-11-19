package com.chi2.web.consulta;

import java.time.Clock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Única entrada de la lógica del formulario, para la web y la API (§5).
 * Primero guarda (desde ahí la consulta no se pierde) y después intenta el aviso, fuera de la transacción.
 */
@Service
public class ConsultaService {

	private static final Logger log = LoggerFactory.getLogger(ConsultaService.class);

	private final ConsultaRepository repository;
	private final ConsultaMapper mapper;
	private final LimitadorConsultas limitador;
	private final AvisoConsultas avisos;
	private final Clock clock;

	public ConsultaService(ConsultaRepository repository, ConsultaMapper mapper, LimitadorConsultas limitador,
			AvisoConsultas avisos, Clock clock) {
		this.repository = repository;
		this.mapper = mapper;
		this.limitador = limitador;
		this.avisos = avisos;
		this.clock = clock;
	}

	/**
	 * Registra una consulta ya validada. Si vino con el campo trampa lleno, no hace nada:
	 * quien llama responde "éxito" igual, para no darle pistas al bot (§6).
	 *
	 * @throws LimiteDeConsultasExcedidoException si la IP superó el límite por hora
	 */
	public void registrar(DatosConsulta datos, String ip) {
		if (datos.esTrampa()) {
			log.info("Consulta descartada: campo trampa completo");
			return;
		}
		limitador.registrarEnvio(ip);
		Consulta consulta = repository.save(mapper.aEntidad(datos, clock.instant()));
		avisos.intentar(consulta);
	}

}

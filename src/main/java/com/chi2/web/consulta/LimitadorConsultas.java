package com.chi2.web.consulta;

import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Límite de consultas por IP en una ventana de una hora (§6). Vive en memoria: no se guarda la IP
 * y los contadores vuelven a cero si la app se reinicia, lo que alcanza para una sola instancia.
 */
@Component
public class LimitadorConsultas {

	private static final Duration VENTANA = Duration.ofHours(1);

	private final Map<String, Deque<Instant>> enviosPorIp = new HashMap<>();
	private final int limitePorHora;
	private final Clock clock;

	public LimitadorConsultas(ConsultasProperties properties, Clock clock) {
		this.limitePorHora = properties.limitePorHora();
		this.clock = clock;
	}

	/** Cuenta un envío de la IP o lanza la excepción si ya llegó al límite. */
	public synchronized void registrarEnvio(String ip) {
		Instant ahora = clock.instant();
		Deque<Instant> envios = enviosPorIp.computeIfAbsent(ip, k -> new ArrayDeque<>());
		descartarVencidos(envios, ahora);
		if (envios.size() >= limitePorHora) {
			throw new LimiteDeConsultasExcedidoException();
		}
		envios.addLast(ahora);
	}

	/** Borra las IP sin envíos recientes para que el mapa no crezca sin fin. */
	@Scheduled(fixedDelayString = "PT10M", initialDelayString = "PT10M")
	public synchronized void limpiar() {
		Instant ahora = clock.instant();
		enviosPorIp.values().forEach(envios -> descartarVencidos(envios, ahora));
		enviosPorIp.values().removeIf(Deque::isEmpty);
	}

	private static void descartarVencidos(Deque<Instant> envios, Instant ahora) {
		Instant limite = ahora.minus(VENTANA);
		while (!envios.isEmpty() && !envios.peekFirst().isAfter(limite)) {
			envios.removeFirst();
		}
	}

}

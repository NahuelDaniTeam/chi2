package com.chi2.web;

import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZoneOffset;

/** Reloj que se puede adelantar a mano, para testear ventanas de tiempo sin esperar. */
public class RelojDeTest extends Clock {

	private Instant ahora;

	public RelojDeTest(Instant inicio) {
		this.ahora = inicio;
	}

	public void adelantar(Duration duracion) {
		ahora = ahora.plus(duracion);
	}

	@Override
	public Instant instant() {
		return ahora;
	}

	@Override
	public ZoneId getZone() {
		return ZoneOffset.UTC;
	}

	@Override
	public Clock withZone(ZoneId zone) {
		return this;
	}

}

package com.chi2.web;

import java.util.TimeZone;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class WebApplication {

	public static void main(String[] args) {
		// Todo en UTC: las fechas se guardan en UTC y se convierten a hora local solo al mostrarlas.
		TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
		SpringApplication.run(WebApplication.class, args);
	}

}

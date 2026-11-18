package com.chi2.web.pagina;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Páginas públicas del sitio (renderizadas con Thymeleaf).
 * Los endpoints JSON para clientes futuros (app nativa) van en el paquete {@code api}, bajo {@code /api}.
 */
@Controller
public class PaginaController {

	@GetMapping("/")
	public String inicio() {
		return "inicio";
	}

	@GetMapping("/servicios")
	public String servicios() {
		return "servicios";
	}

	@GetMapping("/casos")
	public String casos() {
		return "casos";
	}

	@GetMapping("/quienes-somos")
	public String quienesSomos() {
		return "quienes-somos";
	}

	@GetMapping("/contacto")
	public String contacto() {
		return "contacto";
	}

}

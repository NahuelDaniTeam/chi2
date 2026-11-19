package com.chi2.web.pagina;

import com.chi2.web.proyecto.CatalogoProyectos;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Páginas públicas del sitio (renderizadas con Thymeleaf).
 * Los endpoints JSON para clientes futuros (app nativa) van en el paquete {@code api}, bajo {@code /api}.
 */
@Controller
public class PaginaController {

	private final CatalogoProyectos catalogo;

	public PaginaController(CatalogoProyectos catalogo) {
		this.catalogo = catalogo;
	}

	@GetMapping("/")
	public String inicio(Model model) {
		model.addAttribute("proyectos", catalogo.todos());
		return "inicio";
	}

	@GetMapping("/servicios")
	public String servicios() {
		return "servicios";
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

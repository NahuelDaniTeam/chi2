package com.chi2.web.proyecto;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.server.ResponseStatusException;

@Controller
public class ProyectoController {

	private final CatalogoProyectos catalogo;

	public ProyectoController(CatalogoProyectos catalogo) {
		this.catalogo = catalogo;
	}

	@GetMapping("/proyectos")
	public String lista(Model model) {
		model.addAttribute("casos", catalogo.casos());
		model.addAttribute("enDesarrollo", catalogo.enDesarrollo());
		return "proyectos/lista";
	}

	@GetMapping("/proyectos/{slug}")
	public String detalle(@PathVariable String slug, Model model) {
		Proyecto proyecto = catalogo.porSlug(slug)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
		model.addAttribute("proyecto", proyecto);
		return "proyectos/detalle";
	}

}

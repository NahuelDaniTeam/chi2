package com.chi2.web.consulta;

import java.util.List;

import com.chi2.web.contacto.ContactoProperties;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/** Página de contacto y formulario web (§5.1). Funciona sin JavaScript, con Post/Redirect/Get. */
@Controller
public class ConsultaController {

	static final String VISTA = "contacto";

	/** Orden de los campos en pantalla: el foco va al primero con error (§8). */
	private static final List<String> CAMPOS = List.of("nombre", "contacto", "rubro", "mensaje");

	private final ConsultaService service;
	private final ContactoProperties contacto;

	public ConsultaController(ConsultaService service, ContactoProperties contacto) {
		this.service = service;
		this.contacto = contacto;
	}

	@GetMapping("/contacto")
	public String contacto(@RequestParam(required = false) String enviada, Model model) {
		model.addAttribute("enviada", enviada != null);
		if (!model.containsAttribute("consulta")) {
			model.addAttribute("consulta", ConsultaForm.vacio());
		}
		return VISTA;
	}

	@PostMapping("/contacto")
	public String enviar(@Valid @ModelAttribute("consulta") ConsultaForm form, BindingResult errores,
			HttpServletRequest request, RedirectAttributes redirect, Model model) {
		// Con el campo trampa lleno se responde éxito aunque haya errores (§6); el service no guarda nada.
		if (errores.hasErrors() && !form.esTrampa()) {
			model.addAttribute("enviada", false);
			model.addAttribute("primerCampoConError", primerCampoConError(errores));
			return VISTA;
		}
		service.registrar(form, request.getRemoteAddr());
		redirect.addFlashAttribute("nombreEnviado", form.nombre());
		return "redirect:/contacto?enviada";
	}

	@ExceptionHandler(LimiteDeConsultasExcedidoException.class)
	@ResponseStatus(HttpStatus.TOO_MANY_REQUESTS)
	public String limiteExcedido(LimiteDeConsultasExcedidoException ex, HttpServletRequest request, Model model) {
		model.addAttribute("consulta", formDesde(request));
		model.addAttribute("enviada", false);
		model.addAttribute("errorGeneral", ex.getMessage());
		model.addAttribute("contacto", contacto);
		return VISTA;
	}

	private static String primerCampoConError(BindingResult errores) {
		return CAMPOS.stream().filter(errores::hasFieldErrors).findFirst().orElse(null);
	}

	/** Conserva lo que escribió el visitante al mostrar el error de límite. */
	private static ConsultaForm formDesde(HttpServletRequest request) {
		return new ConsultaForm(request.getParameter("nombre"), request.getParameter("contacto"),
				request.getParameter("rubro"), request.getParameter("mensaje"), null);
	}

}

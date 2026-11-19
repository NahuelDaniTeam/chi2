package com.chi2.web.api;

import com.chi2.web.consulta.ConsultaRequest;
import com.chi2.web.consulta.ConsultaResponse;
import com.chi2.web.consulta.ConsultaService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/** {@code POST /api/consultas}, para la app nativa futura (§5.2). Usa el mismo service que la web. */
@RestController
@RequestMapping("/api/consultas")
public class ConsultaApiController {

	private final ConsultaService service;

	public ConsultaApiController(ConsultaService service) {
		this.service = service;
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ConsultaResponse registrar(@Valid @RequestBody ConsultaRequest consulta, BindingResult errores,
			HttpServletRequest request) throws BindException {
		// Con el campo trampa lleno se responde 201 aunque haya errores (§6); el service no guarda nada.
		if (errores.hasErrors() && !consulta.esTrampa()) {
			throw new BindException(errores);
		}
		service.registrar(consulta, request.getRemoteAddr());
		return ConsultaResponse.recibida();
	}

}

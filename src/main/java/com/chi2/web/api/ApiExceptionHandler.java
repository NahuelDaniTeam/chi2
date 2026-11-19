package com.chi2.web.api;

import java.util.LinkedHashMap;
import java.util.Map;

import com.chi2.web.consulta.LimiteDeConsultasExcedidoException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/** Traduce las excepciones de la API a HTTP con {@link ProblemDetail} (§5.2). */
@RestControllerAdvice(basePackageClasses = ApiExceptionHandler.class)
public class ApiExceptionHandler {

	@ExceptionHandler(BindException.class)
	public ProblemDetail validacion(BindException ex) {
		ProblemDetail problema = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST,
				"Hay datos para corregir.");
		problema.setProperty("errores", erroresPorCampo(ex));
		return problema;
	}

	/** JSON malformado o con codificación inválida: 400 sin exponer detalles internos. */
	@ExceptionHandler(HttpMessageNotReadableException.class)
	public ProblemDetail cuerpoIlegible() {
		return ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST,
				"No pudimos leer el pedido. Mandá un JSON válido en UTF-8.");
	}

	@ExceptionHandler(LimiteDeConsultasExcedidoException.class)
	public ProblemDetail limiteExcedido(LimiteDeConsultasExcedidoException ex) {
		return ProblemDetail.forStatusAndDetail(HttpStatus.TOO_MANY_REQUESTS, ex.getMessage());
	}

	private static Map<String, String> erroresPorCampo(BindException ex) {
		Map<String, String> errores = new LinkedHashMap<>();
		for (FieldError error : ex.getFieldErrors()) {
			errores.putIfAbsent(error.getField(), error.getDefaultMessage());
		}
		return errores;
	}

}

package com.chi2.web.contacto;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

/** Expone los datos de contacto a todas las vistas como {@code contacto}. */
@ControllerAdvice
public class ContactoModelAdvice {

	private final ContactoProperties contacto;

	public ContactoModelAdvice(ContactoProperties contacto) {
		this.contacto = contacto;
	}

	@ModelAttribute("contacto")
	public ContactoProperties contacto() {
		return contacto;
	}

}

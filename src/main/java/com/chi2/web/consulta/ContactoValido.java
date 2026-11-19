package com.chi2.web.consulta;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.Payload;

/** "WhatsApp o mail": tiene que ser un mail o un teléfono válido (§3.1). */
@Documented
@Constraint(validatedBy = ContactoValido.Validador.class)
@Target({ ElementType.FIELD, ElementType.PARAMETER, ElementType.RECORD_COMPONENT })
@Retention(RetentionPolicy.RUNTIME)
public @interface ContactoValido {

	String message() default "Dejanos un WhatsApp o un mail válido para responderte.";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};

	class Validador implements ConstraintValidator<ContactoValido, String> {

		@Override
		public boolean isValid(String contacto, ConstraintValidatorContext context) {
			return TipoContacto.detectar(contacto).isPresent();
		}

	}

}

package com.chi2.web;

import com.chi2.web.contacto.ContactoProperties;
import com.chi2.web.proyecto.CatalogoProyectos;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Import;

/** Beans que necesitan los tests {@code @WebMvcTest} para renderizar las vistas. */
@TestConfiguration(proxyBeanMethods = false)
@EnableConfigurationProperties(ContactoProperties.class)
@Import(CatalogoProyectos.class)
public class WebMvcTestConfig {
}

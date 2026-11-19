# CHI2 · Web principal

Sitio público de CHI2: sistemas a medida para negocios chicos de Argentina.

**Stack:** Java 21 · Spring Boot · Thymeleaf · Tailwind CSS · PostgreSQL + Flyway · Maven.

## Correrlo

Requisitos: Java 21 y Docker Desktop abierto.

```bash
./mvnw spring-boot:run   # http://localhost:8080 (levanta Postgres y Mailpit solos)
./mvnw verify            # build + tests
```

Los mails de desarrollo se ven en http://localhost:8025 (Mailpit).

## Documentación

- [`CLAUDE.md`](CLAUDE.md): descripción del proyecto, convenciones, arquitectura y forma de trabajo.
- [`PENDIENTES.md`](PENDIENTES.md): qué falta y quién lo tiene.
- [`docs/`](docs/): visión y alcance, contenido del sitio y especificaciones.

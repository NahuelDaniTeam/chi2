# CHI2 · Web principal

Sitio público y contenedor de la marca. Presenta quiénes somos y qué hacemos, y es la puerta de entrada al área de clientes (portal de acceso, a futuro).

**Estado:** v1 en construcción: páginas, contenido y formulario de contacto hechos; faltan los pendientes de `PENDIENTES.md` para publicar.
**Última actualización:** 2026-11-18

**Documentos de referencia:** `docs/01-vision-alcance.md` (visión, alcance de la v1, principios de diseño,
riesgos y criterio de "v1 terminada") `docs/02-contenido.md` (los textos del sitio: las plantillas usan
esos textos, no se inventan otros) y `docs/03-formulario-contacto.md` (especificación del formulario). Todo lo que se construya tiene que respetarlos; si este archivo y esos
documentos se contradicen, mandan los documentos y se corrige este archivo.

---

## 1. Identidad

- **Nombre de la marca:** CHI2
- **Dominio:** [PENDIENTE]
- **Tono visual:** sobrio y editorial. Fondo claro, tipografía grande, casos contados como historias, sin elementos genéricos de plantilla.
- **Paleta:** [PENDIENTE]
- **Tipografías:** [PENDIENTE]
- **Logo:** propuesta aplicada: isotipo **χ²** (chi cuadrado, de donde sale el nombre) + "CHI2" con el 2 en gris cálido `#a8a29e`. Versiones: horizontal `χ² | CHI2` (encabezado), apilada con "chi cuadrado" (pie) y cuadrado oscuro (favicon `static/favicon.svg` e íconos PNG). El isotipo es el fragmento `isotipo(clase)` de `fragments/layout.html`.

## 2. Presentación

- **Quiénes somos:** estudiantes avanzados de Ingeniería en Sistemas de Información en la UTN FRC, que desarrollan sistemas a medida.
- **Equipo:** Nahuel Luna y Daniel Patolsky.
- **Titular:** "Entendemos tu negocio y construimos la herramienta que necesita." (textos completos en `docs/02-contenido.md`)

## 3. Servicios

- **Sistemas a medida:** turnos, clientes, tareas o lo que el negocio necesite organizar, en una herramienta hecha para su forma de trabajar.
- **Análisis de tu negocio:** propuesta a confirmar (`PENDIENTES.md`).

## 4. Rubros y clientes objetivo

- Barberías
- Peluquerías
- Gestión de tareas
- Negocios en general

## 5. Proyectos (casos)

La v1 muestra dos grupos, en este orden:

| Proyecto | Tipo | Estado |
|---|---|---|
| Seguimiento de camiones recolectores | Trabajo anterior de Daniel (caso real) | Detalle pendiente |
| Cálculo de medidas para una imprenta | Trabajo anterior de Daniel (caso real) | Detalle pendiente |
| Sistema para barberías | En desarrollo (repo `chi2-peluqueria`) | Detallado en `docs/02-contenido.md` §4 |
| Gestión de tareas | En desarrollo (repo `chi2-gestor_tareas`) | Detallado en `docs/02-contenido.md` §4 |

Cada uno se presenta con problema, solución, tecnologías y estado (los casos, además, con resultado). Los proyectos en desarrollo pasan a "caso" cuando están terminados. Los proyectos de facultad se rotulan como "proyecto académico".

Solo se muestran como clientes los proyectos reales cuyo cliente haya dado permiso. No se usan testimonios ficticios.

## 6. Estructura de páginas

1. **Inicio:** titular, servicios destacados, casos y proyectos en desarrollo, y llamado a la acción.
2. **Servicios:** qué hacemos, cómo trabajamos (5 pasos) y con qué tipo de negocio trabajamos.
3. **Proyectos:** trabajos anteriores y proyectos en desarrollo, con página de detalle de cada uno.
4. **Quiénes somos:** equipo y presentación general.
5. **Contacto:** botón principal a WhatsApp y formulario como alternativa.
6. **Área de clientes:** fuera de la v1.

**Acción principal en todo el sitio:** escribir por WhatsApp (el formulario es la secundaria), siempre con el compromiso "Respondemos en 24 horas hábiles".

## 7. Contacto

- **Email:** [PENDIENTE]
- **WhatsApp:** [PENDIENTE]
- **LinkedIn:** [PENDIENTE]

## 8. Stack técnico

- **Backend:** Java 21 con Spring Boot
- **Plantillas:** Thymeleaf
- **Estilos:** Tailwind CSS
- **Base de datos:** PostgreSQL, con migraciones Flyway
- **Pruebas:** JUnit 5 y Testcontainers
- **Build:** Maven
- **Hosting inicial:** servicio en la nube (Render o Railway), a confirmar
- **Diseño previo:** Figma, antes de codificar

**Decisión de arquitectura:** el backend se diseña como API REST desde el inicio, para que una app nativa futura consuma los mismos endpoints.

### Cómo correr el proyecto

Requisitos: Java 21, Docker Desktop **abierto** (la base local y los tests usan Postgres en Docker). Node no hace falta: Maven lo descarga solo.

| Qué | Comando |
|---|---|
| Levantar la app (http://localhost:8080) | `./mvnw spring-boot:run` — arranca Postgres solo vía `compose.yaml` |
| Build completo + todos los tests | `./mvnw verify` |
| Recompilar CSS mientras se editan plantillas | `npm run watch:css` (en otra terminal, con la app corriendo) |
| Ver los mails del formulario de contacto | http://localhost:8025 (Mailpit, arranca con `compose.yaml`). Para que lleguen avisos, levantar la app con `CHI2_CONSULTAS_DESTINATARIOS=a@prueba.test,b@prueba.test` |

Si Postgres ya estaba corriendo de antes, Spring Boot no levanta los servicios nuevos de `compose.yaml`: en ese caso, `docker compose up -d`.

### Estructura y convenciones

```
docs/                         análisis y especificaciones (NN-tema.md)
PENDIENTES.md                 hoja de pendientes del equipo (§10)
compose.yaml                  Postgres + Mailpit para desarrollo
src/main/java/com/chi2/web/
├── WebApplication.java
├── ConfiguracionGeneral.java Clock (UTC) y @EnableScheduling
├── pagina/                   páginas públicas fijas (inicio, servicios, quiénes somos)
├── proyecto/                 proyectos y casos (contenido en CatalogoProyectos)
├── consulta/                 página /contacto y su formulario: dominio, service, repository, mail (docs/03)
├── contacto/                 datos de contacto públicos (WhatsApp, mail) disponibles en todas las vistas
└── api/                      @RestController bajo /api/... y su manejo de errores
src/main/resources/
├── templates/                vistas Thymeleaf (fragments/layout.html = piezas compartidas)
├── static/                   favicon e íconos en la raíz; imágenes en img/<sección>/
└── db/migration/             migraciones Flyway
src/main/frontend/main.css    entrada de Tailwind
src/test/java/...             tests, mismo paquete que la clase que prueban
```

- Una funcionalidad nueva va en su propio paquete con sus capas (ver "Arquitectura en capas"); su `@RestController` va en `api/`.
- `contacto/` — datos de contacto (`chi2.contacto.*` en `application.properties`, o variables `CHI2_CONTACTO_WHATSAPP` / `CHI2_CONTACTO_EMAIL`). Las vistas los reciben como `contacto` (ej: `${contacto.linkWhatsapp()}`). No confundir con `consulta/`, que es el formulario.
- `templates/fragments/layout.html` tiene las piezas compartidas: `head(titulo, descripcion)`, `encabezado`, `cabecera(etiqueta, titulo, bajada)`, `logo`, `isotipo(clase)`, `icono-whatsapp`, `cta`, `tarjeta(p)` y `pie`. Diseñar primero para celular.
- Imágenes: versión `.webp` + `.jpg` de respaldo (`<picture>`), optimizadas y sin metadatos de ubicación. Los originales no se commitean.
- `src/main/frontend/main.css` — entrada de Tailwind v4; los tokens de marca van en el bloque `@theme`. El CSS compilado (`static/css/main.css`) se genera en el build y **no se commitea**.
- `src/main/resources/db/migration/` — migraciones Flyway `V<n>__descripcion.sql`. **Nunca editar una migración ya mergeada a `main`**; crear una nueva. Si los dos crean la misma versión `V<n>` en ramas distintas, quien mergea segundo renumera la suya.
- El esquema lo define solo Flyway (`ddl-auto=validate`).
- La app corre en **UTC** (fechas guardadas en UTC; convertir a hora argentina solo al mostrar).
- Todo endpoint o página nueva lleva su test (`@WebMvcTest` para controladores; `@SpringBootTest` + Testcontainers cuando toca la base).

### Arquitectura en capas (regla para todo el código)

El código se organiza **por funcionalidad** (`com.chi2.web.<funcionalidad>`, ej: `consulta`, `proyecto`) y, dentro de cada una, **en capas**:

| Capa | Clase | Responsabilidad | No hace |
|---|---|---|---|
| Controller | `XxxController` (páginas Thymeleaf) · `api/XxxApiController` (REST, bajo `/api/...`) | Recibe el pedido HTTP, valida el DTO de entrada (`@Valid`), llama al service y devuelve la vista o el DTO de salida. | Lógica de negocio, acceso a repositorios, manejo de entidades. |
| Service | `XxxService` | Lógica de negocio, reglas y transacciones (`@Transactional`). Recibe y devuelve DTOs o tipos de dominio. | Conocer HTTP (`HttpServletRequest`, `Model`, códigos de estado). |
| Repository | `XxxRepository` (Spring Data JPA) | Acceso a datos. Consultas derivadas o `@Query`. | Lógica de negocio. |
| Entity | `Xxx` (`@Entity`) | Modelo persistido, con su estado e invariantes. | Salir de la capa de service: **nunca** se expone en una vista ni en la API. |
| DTO | `XxxForm` / `XxxRequest` (entrada) · `XxxResponse` (salida) | `record` de Java con Bean Validation. Es el contrato con la vista o la API. | Lógica. |
| Mapper | `XxxMapper` | Convierte entre entidad y DTO, a mano (sin librerías de mapeo). | Lógica de negocio. |

- **Las dependencias van en una sola dirección:** controller → service → repository → entity. Nunca al revés ni salteando capas (un controller no usa un repository).
- Lo que se conecta con el exterior (mail, servicios externos, reloj) va detrás de una **interfaz** propia del dominio (ej: `NotificadorConsultas`), con su implementación aparte (ej: `NotificadorConsultasMail`).
- Errores: excepciones de dominio propias (ej: `LimiteDeConsultasExcedidoException`), traducidas a HTTP en un `@RestControllerAdvice` con `ProblemDetail`, no con `try/catch` en cada controller.
- Configuración en records `@ConfigurationProperties`, no `@Value` sueltos.
- Si el contenido es fijo y no hay base (como `CatalogoProyectos`), la clase cumple el rol de repository y se respeta el mismo flujo.

### Principios de diseño

**SOLID**, aplicado así en este proyecto:

- **S (responsabilidad única):** una clase, un motivo para cambiar. Ej: `ConsultaService` registra la consulta; el armado del mail vive en otra clase.
- **O (abierto/cerrado):** se extiende sumando implementaciones, no editando las existentes. Ej: avisar por WhatsApp sería una nueva implementación de `NotificadorConsultas`.
- **L (sustitución de Liskov):** toda implementación de una interfaz respeta su contrato; los tests de la interfaz pasan con cualquier implementación.
- **I (segregación de interfaces):** interfaces chicas y específicas, no "servicios dios".
- **D (inversión de dependencias):** las capas de arriba dependen de abstracciones. **Inyección por constructor** con campos `final`; nada de `@Autowired` en campos. Inyectar `java.time.Clock` en vez de llamar a `Instant.now()`, para poder testear fechas.

**Además:**

- **KISS y YAGNI:** la solución más simple que cumpla la especificación. No crear interfaces "por las dudas": solo en los límites con lo externo o cuando hay más de una implementación real.
- **DRY:** una regla de negocio vive en un solo lugar (ej: el formulario web y la API usan el mismo service).
- **Nombres del dominio en español** (`Consulta`, `registrar`, `estadoAviso`), consistentes con los documentos de `docs/`.
- **Métodos cortos y con nombre que diga qué hacen.** Si un comentario explica *qué* hace un bloque, ese bloque es un método.
- **Sin lógica en las plantillas:** Thymeleaf solo muestra lo que le prepara el controller.
- **Tests por capa:** `@WebMvcTest` con el service mockeado para controllers; tests unitarios para services y mappers; `@DataJpaTest` o `@SpringBootTest` + Testcontainers para repositories y flujos completos.

## 9. Decisiones tomadas

- El sitio público y el área de clientes son partes separadas.
- La marca se presenta como estudiantes avanzados de Ingeniería en Sistemas de Información en la UTN FRC, en "Quiénes somos", sin destacarlo en el titular.
- Tono visual sobrio y editorial.

## 10. Pendientes (`PENDIENTES.md`)

Los pendientes del equipo se registran **solo** en `PENDIENTES.md`, en la raíz del repo. No se duplican acá ni en otros documentos; esos documentos marcan `[PENDIENTE]` en el lugar y la tarea se anota en la hoja.

**Reglas para Claude:**

1. **Identificar con quién hablás:** usá `git config user.name`. `NahuelLuna` es Nahuel y `DanielPatolsky` es Daniel.
2. **Al arrancar cada sesión**, antes de responder lo primero que te pidan, leé `PENDIENTES.md` (la versión de `origin/main` si la rama local está atrasada) y mostrale al usuario en una lista corta:
   - lo que tiene pendiente en su sección (**"Para Nahuel"** o **"Para Daniel"**) y en **"Para los dos"**;
   - cuántos pendientes tiene el compañero, sin el detalle salvo que lo pida.
3. **Al terminar una tarea**, si cumple un pendiente, movelo a **Hechos** con la fecha. Si surge algo nuevo que tiene que hacer el compañero o el usuario, cargalo en la sección correcta con el formato de la hoja (contexto, qué entregar, dónde). **Escribilo para alguien que no estuvo en la conversación**: incluí lo ya decidido y lo que se sabe hasta ahora. Avisale al usuario qué agregaste o cerraste.
4. **Cargar y cerrar pendientes** se hace en la misma rama y PR del trabajo que los genera o resuelve; no en un commit directo a `main`.
5. Si el usuario pide "dejale un pendiente a Daniel" (o a Nahuel), se agrega en la sección del otro con `cargado por <quien>, <fecha>`.

---

## 11. Equipo y forma de trabajo

Trabajamos dos personas en paralelo, cada una con su propio Claude. Estas reglas aplican
a cualquier sesión de Claude en este repo, sea de quien sea (Nahuel o Daniel).

### Ramas

- `main` es la rama estable. **Nunca se commitea ni se pushea directo a `main`.**
- Cada uno trabaja en su rama: `<usuario-github>/<tema>` (ej: `NahuelLuna/login`).
- Ramas cortas y enfocadas en una sola cosa. Mejor varias PRs chicas que una gigante.
- Todo entra a `main` por Pull Request (squash merge). Borrar la rama después del merge.

### Mantenerse sincronizado

- Antes de arrancar una tarea y antes de abrir/actualizar una PR:
  `git fetch origin && git rebase origin/main`.
- Integrar seguido (al menos una vez por sesión) para que los conflictos sean chicos.
- En la rama propia se puede reescribir historia, pero pushear siempre con
  `git push --force-with-lease`, nunca `--force` a secas. Jamás force-push a `main`.

### Resolución de conflictos (regla para Claude)

Cuando un rebase, merge o pull genera conflictos, **Claude los resuelve solo, sin frenar
a preguntar**, siguiendo este proceso:

1. Entender la intención de cada lado antes de tocar nada: leer los commits involucrados
   (`git log origin/main..HEAD`, `git log HEAD..origin/main`, `git show <sha>`) y el código
   alrededor del conflicto.
2. Resolver preservando **las dos** funcionalidades. Nunca descartar en silencio el trabajo
   del compañero (ni el propio) eligiendo "ours"/"theirs" de un archivo entero.
3. Archivos generados (lockfiles, builds, snapshots): no mergear a mano; regenerarlos con
   la herramienta correspondiente después de resolver el resto.
4. Verificar después de resolver: que compile/levante y que pasen los tests (cuando existan).
5. Al terminar, contarle al usuario en un resumen corto qué conflictos hubo y cómo se
   resolvió cada uno.

**Única excepción — preguntar antes de resolver:** si los dos lados cambian el mismo
comportamiento de formas contradictorias (no se pueden conservar ambos), Claude explica
las dos opciones y le pregunta al usuario cuál va.

### Commits

- Commits chicos y descriptivos, en español o inglés pero consistentes dentro de una PR.
- Formato sugerido: `tipo: descripción` (`feat`, `fix`, `refactor`, `docs`, `chore`, `test`).

### Worktrees (Orca)

- Trabajamos con git worktrees: el stack de `git stash` es compartido entre todos los
  worktrees. No usar `git stash` / `git stash pop` a secas; preferir un commit WIP temporal.

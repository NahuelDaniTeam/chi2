# CHI2 · Web principal

Sitio público y contenedor de la marca. Presenta quiénes somos y qué hacemos, y es la puerta de entrada al área de clientes (portal de acceso, a futuro).

**Estado:** esqueleto inicial. Pendiente de completar por el equipo.
**Última actualización:** 2026-11-18

---

## 1. Identidad

- **Nombre de la marca:** CHI2
- **Dominio:** [PENDIENTE]
- **Tono visual:** sobrio y editorial. Fondo claro, tipografía grande, casos contados como historias, sin elementos genéricos de plantilla.
- **Paleta:** [PENDIENTE]
- **Tipografías:** [PENDIENTE]
- **Logo:** [PENDIENTE]

## 2. Presentación

- **Quiénes somos:** estudiantes avanzados de Ingeniería en Sistemas de Información en la UTN FRC, que desarrollan sistemas a medida.
- **Equipo:** Nahuel Luna y Daniel Patolsky.
- **Mensaje principal (titular):** [PENDIENTE: una frase que diga qué hacemos y para quién]

## 3. Servicios

- **Sistemas a medida:** [PENDIENTE: descripción en una o dos frases]
- **Otros servicios:** [PENDIENTE]

## 4. Rubros y clientes objetivo

- Barberías
- Peluquerías
- Gestión de tareas
- Negocios en general

## 5. Proyectos (casos)

Cada caso se presenta con problema, solución, tecnologías y resultado. Los proyectos de facultad se rotulan como "proyecto académico".

| Caso | Tipo | Estado |
|---|---|---|
| Caso 1 | [PENDIENTE: real o académico] | [PENDIENTE] |
| Caso 2 | [PENDIENTE: real o académico] | [PENDIENTE] |

Solo se muestran como clientes los proyectos reales cuyo cliente haya dado permiso. No se usan testimonios ficticios.

## 6. Estructura de páginas

1. **Inicio:** titular, servicios destacados y dos o tres casos.
2. **Servicios:** qué hacemos y con qué tipo de negocio trabajamos.
3. **Casos:** lista y detalle de cada caso.
4. **Quiénes somos:** equipo y presentación general.
5. **Contacto:** formulario o datos de contacto.
6. **Área de clientes:** enlace al portal de acceso (a futuro, no en la primera versión).

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

## 9. Decisiones tomadas

- El sitio público y el área de clientes son partes separadas.
- La marca se presenta como estudiantes avanzados de Ingeniería en Sistemas de Información en la UTN FRC, en "Quiénes somos", sin destacarlo en el titular.
- Tono visual sobrio y editorial.

## 10. Pendientes para el equipo

- [ ] Definir dominio y confirmar disponibilidad.
- [ ] Definir paleta, tipografías y logo.
- [ ] Redactar el titular y la descripción de servicios.
- [ ] Completar los datos de contacto.
- [ ] Documentar los dos proyectos (problema, tecnologías, resultado).
- [ ] Pedir permiso a los clientes reales para mostrarlos.
- [ ] Elegir hosting.
- [ ] Bocetar las páginas en Figma.

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

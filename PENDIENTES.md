# Pendientes · CHI2

Hoja compartida de tareas pendientes entre Nahuel y Daniel. Claude la lee al arrancar cada sesión y le avisa a cada uno qué tiene pendiente (ver `CLAUDE.md` §10).

**Cómo se escribe un pendiente:** tiene que poder entenderlo alguien que no estuvo en la charla donde surgió. Cada ítem lleva:
- **Contexto:** por qué existe y qué ya está decidido.
- **Qué entregar:** concreto y verificable.
- **Dónde:** el archivo o la sección donde va el resultado.
- La línea `cargado por <quien>, <AAAA-MM-DD>`.

Al completar un ítem se mueve a **Hechos** con la fecha. Los hechos de más de un mes se pueden borrar.

**Documentos de referencia:** `docs/01-vision-alcance.md` (qué entra en la v1 y por qué) y `docs/02-contenido.md` (textos del sitio).

---

## Para Daniel

### 1. Detallar el caso "seguimiento de camiones recolectores"
- **Contexto:** decidimos que la v1 del sitio muestre tus trabajos anteriores como **casos reales**. Son la mejor prueba de que sabemos hacer lo que decimos, más que los proyectos en desarrollo. Nahuel solo sabe que es un prototipo para la ciudad de Santiago del Estero.
- **Qué entregar:** respuestas cortas, con tus palabras (Claude las redacta después):
  1. **Problema:** qué le pasaba a la ciudad o al área antes y qué no podían ver o controlar.
  2. **Solución:** qué hace el sistema. Las 3 o 4 funciones principales, por ejemplo mapa en vivo o historial de recorridos.
  3. **Tecnologías:** lenguajes, frameworks, mapas, GPS o hardware.
  4. **Resultado o estado:** si quedó como prototipo, si se usó o si se presentó, y cualquier número que sirva (cantidad de camiones, kilómetros, etc.).
  5. **Tu rol:** si lo hiciste solo o en equipo, y qué parte hiciste vos.
  6. **Material:** capturas o fotos que se puedan mostrar.
- **Dónde:** `docs/02-contenido.md` §4, "Trabajos anteriores", o contáselo a Claude y que lo cargue.
- cargado por Nahuel, 2026-11-18

### 2. Detallar el caso "cálculo de medidas para una imprenta"
- **Contexto:** es el segundo caso real de la v1, por la misma razón que el anterior. Nahuel solo sabe que es un sistema para calcular medidas.
- **Qué entregar:** las mismas 6 respuestas que en el pendiente 1: problema, solución, tecnologías, resultado, tu rol y material. Sumá también qué se calculaba exactamente (medidas de qué, para qué productos) y cómo se hacía antes del sistema.
- **Dónde:** `docs/02-contenido.md` §4.
- cargado por Nahuel, 2026-11-18

### 3. Definir cómo se nombra a los clientes de esos dos casos
- **Contexto:** la regla del proyecto (`CLAUDE.md` §5) es que un cliente real solo se nombra si dio permiso. Si no hay permiso, el caso se muestra anonimizado. Por ejemplo, "Una imprenta de Córdoba" en lugar del nombre comercial.
- **Qué entregar:** para cada caso, una de dos: "se nombra, con permiso de <quién>" o "anonimizado como <texto>". Si hace falta pedir permiso, avisá a quién y cuándo.
- **Dónde:** `docs/02-contenido.md` §4.
- cargado por Nahuel, 2026-11-18

### 4. Tu rol y tu presentación en "Quiénes somos"
- **Contexto:** la página presenta a cada uno con un nombre, un rol corto y una o dos frases de experiencia. El de Nahuel ya está: "Análisis funcional y desarrollo", con su pasantía de analista funcional en EPEC desde mayo de 2026. El tuyo hoy dice `[PENDIENTE]`. Lo que tenemos de vos son los dos trabajos de arriba.
- **Qué entregar:**
  - un **rol** de 2 a 4 palabras, por ejemplo "Desarrollo backend y producto";
  - en qué sos fuerte o qué te gusta hacer;
  - cualquier experiencia laboral o proyecto que quieras mencionar.
- **Dónde:** `docs/02-contenido.md` §5.
- cargado por Nahuel, 2026-11-18

### 5. Revisar el borrador de contenido
- **Contexto:** Nahuel y Claude redactaron todos los textos del sitio. Ya están decididos:
  - el titular: "Entendemos tu negocio y construimos la herramienta que necesita";
  - el mensaje: analizamos el negocio antes de programar;
  - que la primera charla es gratis y que damos soporte después de la entrega;
  - que se nombra a EPEC.
- **Qué entregar:** leer `docs/02-contenido.md` completo y marcar lo que no te convenza. Lo podés hablar con Nahuel o editar directo en una PR.
- **Dónde:** `docs/02-contenido.md`.
- cargado por Nahuel, 2026-11-18

## Para Nahuel

_(nada por ahora)_

## Para los dos

### 6. Detallar los proyectos en desarrollo: barbería y gestión de tareas
- **Contexto:** son los dos sistemas que vamos a construir. En el sitio aparecen como "en desarrollo" (decidido en `docs/01-vision-alcance.md` §6). Hoy sus tarjetas tienen textos `[PENDIENTE]` en `src/main/java/com/chi2/web/proyecto/CatalogoProyectos.java`.
- **Qué entregar,** para cada proyecto:
  - un resumen en una frase;
  - el problema concreto del negocio;
  - 3 o 4 funciones principales;
  - las tecnologías;
  - en qué estado está.
- **Dónde:** `docs/02-contenido.md` §4. Después se pasa a `CatalogoProyectos`.
- cargado por Nahuel, 2026-11-18

### 7. Confirmar "Análisis de tu negocio" como segundo servicio
- **Contexto:** la página de inicio muestra dos servicios. El primero es "Sistemas a medida". Para el segundo, Claude propuso "Análisis de tu negocio": relevar los procesos del cliente y mostrarle dónde pierde tiempo. Sale de la experiencia de Nahuel como analista funcional.
- **Qué entregar:** sí o no. Si es no, proponer otro servicio, como páginas web, mantenimiento o automatizar planillas.
- **Dónde:** `docs/02-contenido.md` §2.
- cargado por Nahuel, 2026-11-18

### 8. Datos de contacto
- **Contexto:** el botón principal del sitio es WhatsApp y el formulario manda un mail a los dos. Sin estos datos no se puede publicar.
- **Qué entregar:**
  - el número de WhatsApp que se va a mostrar (¿de quién es?);
  - el mail que recibe las consultas (¿uno compartido o el de cada uno?);
  - el LinkedIn de cada uno o uno de CHI2.
- **Dónde:** `CLAUDE.md` §7. En el código van en `application.properties` como `chi2.contacto.*`.
- cargado por Nahuel, 2026-11-18

### 9. Dominio
- **Contexto:** el presupuesto es de hasta USD 10 por mes entre dominio y hosting (`docs/01-vision-alcance.md` §7). El sitio se dirige a toda Argentina.
- **Qué entregar:** el nombre elegido (por ejemplo `chi2.com.ar` o `chi2.dev`), con la disponibilidad y el precio verificados.
- **Dónde:** `CLAUDE.md` §1.
- cargado por Nahuel, 2026-11-18

### 10. Paleta, tipografías y logo
- **Contexto:** el tono visual decidido es sobrio y editorial: fondo claro, tipografía grande y nada que parezca de plantilla. Los colores se definen como tokens en `src/main/frontend/main.css`, en el bloque `@theme`. Hoy existen `fondo`, `tinta` y `tenue`.
- **Qué entregar:**
  - 1 o 2 tipografías de Google Fonts;
  - la paleta: fondo, texto, texto secundario y un color de acento;
  - un logo o, al menos, el logotipo "CHI2".
- **Dónde:** `CLAUDE.md` §1 y `main.css`.
- cargado por Nahuel, 2026-11-18

### 11. Herramienta de analítica
- **Contexto:** la v1 lleva estadísticas de visitas. Tiene que ser liviana y sin cookies, para no poner lento el sitio ni necesitar un aviso de cookies. Las candidatas son Plausible (paga), Umami (gratis si se aloja uno mismo, o con plan en la nube) y GoatCounter (gratis).
- **Qué entregar:** la herramienta elegida y su costo, que tiene que entrar en el presupuesto.
- **Dónde:** `docs/01-vision-alcance.md` §11.
- cargado por Nahuel, 2026-11-18

### 12. Hosting
- **Contexto:** la app es Spring Boot con PostgreSQL. El CLAUDE.md menciona Render o Railway, a confirmar. El límite es de USD 10 por mes con el dominio incluido, y el plan no puede dormirse por inactividad.
- **Qué entregar:** el proveedor y el plan elegidos, con su costo mensual.
- **Dónde:** `CLAUDE.md` §8.
- cargado por Nahuel, 2026-11-18

### 13. Bocetos en Figma
- **Contexto:** la regla es diseñar en Figma antes de codificar las pantallas definitivas. Las páginas son Inicio, Servicios, Proyectos con su detalle, Quiénes somos y Contacto, y se diseñan primero para celular.
- **Qué entregar:** el link al archivo de Figma con al menos Inicio en versión celular y escritorio.
- **Dónde:** pegar el link en `CLAUDE.md` §8.
- cargado por Nahuel, 2026-11-18

### 14. Decidir si hay blog
- **Contexto:** quedó como "sin decidir" y no entra en la v1. Sirve para el SEO, pero lleva trabajo constante.
- **Qué entregar:** "sí, en la v2", "más adelante" o "no".
- **Dónde:** `docs/01-vision-alcance.md` §6.
- cargado por Nahuel, 2026-11-18

---

## Hechos

- [x] Visión y alcance de la v1. (`docs/01-vision-alcance.md`) · 2026-11-18
- [x] Elegir el titular: "Entendemos tu negocio y construimos la herramienta que necesita". · 2026-11-18
- [x] Decidir si se nombra a EPEC: sí. · 2026-11-18
- [x] Definir "Cómo trabajamos": la primera charla es gratis y hay soporte después de la entrega. · 2026-11-18

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

### 18. Pasar los links de tus proyectos ya realizados
- **Contexto:** tus dos trabajos (seguimiento de camiones recolectores e imprenta) ya aparecen como casos en `/proyectos`, pero sin ningún link. Un link a algo que se pueda ver o probar es la prueba más fuerte que podemos dar. Hoy el modelo `Proyecto` (`src/main/java/com/chi2/web/proyecto/Proyecto.java`) no tiene campo para links: hay que sumarlo cuando estén.
- **Qué entregar,** para cada proyecto, los links que existan y se puedan mostrar públicamente:
  - **demo o sitio en funcionamiento;**
  - **repositorio** (GitHub o similar), solo si es público o se puede hacer público sin exponer código ni datos del cliente;
  - **video o capturas,** si no hay demo.
  - Aclarar cuáles son públicos y cuáles son solo para mostrar en una reunión. Si un link deja ver datos o el nombre del cliente, aplica el pendiente 3 (permiso).
- **Dónde:** pegarlos en `docs/02-contenido.md` §4, debajo de cada caso. Después se suma un campo `links` al modelo `Proyecto` y se cargan en `CatalogoProyectos`.
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
  - el mail personal de cada uno donde llegan los avisos de consultas. Ya está decidido que les llega a los dos, cada uno en su mail personal (`docs/03-formulario-contacto.md` §2). Esos mails no van al repo: se cargan en el hosting como `CHI2_CONSULTAS_DESTINATARIOS`;
  - el LinkedIn de cada uno o uno de CHI2.
- **Dónde:** `CLAUDE.md` §7. En el código van en `application.properties` como `chi2.contacto.*`.
- cargado por Nahuel, 2026-11-18

### 9. Dominio
- **Contexto:** el presupuesto es de hasta USD 10 por mes entre dominio y hosting (`docs/01-vision-alcance.md` §7). El sitio se dirige a toda Argentina.
- **Qué entregar:** el nombre elegido (por ejemplo `chi2.com.ar` o `chi2.dev`), con la disponibilidad y el precio verificados.
- **Dónde:** `CLAUDE.md` §1.
- cargado por Nahuel, 2026-11-18

### 10. Paleta, tipografías y logo
- **Contexto:** el tono visual decidido es sobrio y editorial: fondo claro, tipografía grande y nada que parezca de plantilla. Hay una **propuesta provisoria ya aplicada** en el sitio: títulos en Instrument Serif, texto en Inter, fondo marfil `#f7f4ee`, tinta `#191714` y acento terracota `#b5532c`. Se puede confirmar o reemplazar: los colores están en el bloque `@theme` de `src/main/frontend/main.css` y las fuentes en el `<link>` de `fragments/layout.html`. Logo: hay una **propuesta aplicada** basada en los bocetos de Nahuel: el isotipo χ² (chi cuadrado) + "CHI2" con el 2 en gris cálido; ver `CLAUDE.md` §1.
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

### 15. Formulario de contacto
- **Contexto:** es la acción secundaria del sitio (la principal es WhatsApp) y entra en la v1 (`docs/01-vision-alcance.md` §6). La página `/contacto` hoy tiene un recuadro `[PENDIENTE]`. **Está todo especificado en `docs/03-formulario-contacto.md`:** campos y validaciones, flujo, endpoints (`POST /contacto` y `POST /api/consultas`), migración, aviso por Gmail SMTP a los dos, reintentos si el mail falla, anti-spam (campo trampa y 5 envíos por hora por IP), borrado a los 3 meses y 12 criterios de aceptación.
- **Qué entregar:** el formulario funcionando según la especificación, con un test por criterio de aceptación (§10). Para programarlo no hace falta nada más, porque los mails se prueban con Mailpit en local. Para publicarlo hacen falta los pendientes 8 y 16.
- **Dónde:** código en el paquete nuevo `src/main/java/com/chi2/web/consulta/` (diseño de clases en la especificación, §9.3), vista `contacto.html`. Lo agarra quien tenga tiempo primero: avisar al otro antes de arrancar para no duplicar. Si algo de la especificación no cierra al programarlo, se corrige primero el documento.
- cargado por Nahuel, 2026-11-18

### 16. Cuenta Gmail para enviar los avisos del formulario
- **Contexto:** el sitio manda los avisos de consultas por Gmail SMTP (`docs/03-formulario-contacto.md` §2). Hace falta una cuenta remitente. Recomendado: una cuenta nueva de CHI2 (por ejemplo `chi2.consultas@gmail.com`), no la personal de ninguno, para que no dependa de una sola persona. Gmail pide tener activada la verificación en dos pasos para generar una contraseña de aplicación.
- **Qué entregar:**
  - la cuenta creada, con la verificación en dos pasos activa y los dos con acceso;
  - una contraseña de aplicación generada para el sitio.
  - Las credenciales **no** van al repo: se cargan en el hosting como `CHI2_MAIL_USUARIO` y `CHI2_MAIL_CLAVE` y se comparten entre ustedes por un medio seguro, como un gestor de contraseñas.
- **Dónde:** variables de entorno del hosting (pendiente 12). En el repo solo se anota qué cuenta es, en `CLAUDE.md` §7.
- cargado por Nahuel, 2026-11-18

### 17. Verificar qué pide la Ley 25.326 para las consultas
- **Contexto:** el formulario guarda nombre, contacto y mensaje durante 3 meses. El aviso actual dice: "Usamos tus datos solo para responderte y los borramos a los 3 meses". Falta confirmar dos cosas: si hay que inscribir la base de datos ante la AAIP (Agencia de Acceso a la Información Pública) y qué tiene que decir como mínimo el aviso de privacidad (finalidad, plazo y cómo pedir que se borren los datos).
- **Qué entregar:** sí o no sobre la inscripción, con la fuente, y el texto final del aviso. Si hace falta, también una página corta de "Privacidad" enlazada desde el pie.
- **Dónde:** `docs/03-formulario-contacto.md` §8 y §12.
- cargado por Nahuel, 2026-11-18

---

## Hechos

- [x] Logo χ² aplicado como propuesta: encabezado, pie, favicon e íconos para celular. · 2026-11-18
- [x] Rediseño visual con paleta y tipografías provisorias, revisado en celular y escritorio. Los textos nuevos que sumó están en `docs/02-contenido.md` §7, como propuesta. · 2026-11-18
- [x] Fotos del equipo en "Quiénes somos" (recortadas 4:5, optimizadas y sin datos de ubicación, en `static/img/equipo/`). · 2026-11-18
- [x] Pasar los textos de `docs/02-contenido.md` a las páginas y sumar los trabajos anteriores como casos. Lo que falta del contenido sigue marcado `[PENDIENTE]` en el sitio (pendientes 1 a 4, 6 y 7). · 2026-11-18
- [x] Visión y alcance de la v1. (`docs/01-vision-alcance.md`) · 2026-11-18
- [x] Elegir el titular: "Entendemos tu negocio y construimos la herramienta que necesita". · 2026-11-18
- [x] Decidir si se nombra a EPEC: sí. · 2026-11-18
- [x] Definir "Cómo trabajamos": la primera charla es gratis y hay soporte después de la entrega. · 2026-11-18

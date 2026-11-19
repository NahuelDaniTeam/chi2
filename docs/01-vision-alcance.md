# Visión y alcance · Web principal CHI2

**Estado:** borrador v0.1
**Última actualización:** 2026-11-18
**Autores:** Nahuel Luna, Daniel Patolsky

---

## 1. Problema

Muchos negocios chicos de Argentina (barberías, peluquerías y comercios en general) se organizan con cuadernos, planillas o grupos de WhatsApp. Turnos, tareas, clientes y pagos quedan dispersos. Las soluciones genéricas no se adaptan a su forma de trabajar o les resultan caras y complejas.

CHI2 desarrolla sistemas a medida para esos negocios, pero hoy no tiene un lugar donde presentarse. Cuando alguien pregunta "¿quiénes son?, ¿qué hacen?", no hay un enlace para mandarle que transmita profesionalismo y confianza.

## 2. Visión

Un sitio sobrio y editorial que, en pocos segundos, deje claro **qué hacemos, para quién y cómo empezar a hablar con nosotros**, y que sirva como carta de presentación del equipo.

## 3. Objetivos de la v1

El sitio tiene tres objetivos, con el mismo peso:

1. **Conseguir consultas nuevas** de negocios que no nos conocen.
2. **Respaldar los contactos existentes:** ser el enlace que mandamos a quien nos conoció por recomendación, WhatsApp o LinkedIn.
3. **Presentarnos bien:** mostrar quiénes somos, cómo trabajamos y qué estamos construyendo, con una imagen profesional y vendible.

## 4. Público objetivo

- **Geografía:** Argentina, con trabajo remoto.
- **Rubros:** barberías, peluquerías, negocios que necesitan gestión de tareas y planificación, y negocios en general.
- **Perfil típico:** dueño o encargado de un negocio chico, sin conocimientos técnicos, que se informa desde el celular y prefiere escribir por WhatsApp.

> El detalle de perfiles y recorridos va en `02-usuarios.md`.

## 5. Principios de diseño y contenido

Estos principios toman como referencia [B2B Website Design (K6 Agency)](https://www.k6agency.com/b2b-website-design/), adaptada a nuestro caso.

- **Claridad antes que estética:** la primera pantalla dice el resultado que ofrecemos, para quién es y cuál es el siguiente paso.
- **Jerarquía simple:** un mensaje principal, uno de apoyo y una acción evidente por pantalla.
- **Específicos, no genéricos:** hablamos a negocios chicos y a sus problemas concretos, no a "empresas" en abstracto.
- **Prueba de capacidad cerca de la afirmación:** cada promesa va acompañada de algo que la respalde (proceso, proyecto en desarrollo, tecnologías).
- **Honestidad:** no hay testimonios ni clientes ficticios. Los proyectos en desarrollo y los proyectos académicos se rotulan como tales.
- **La velocidad también genera confianza:** el sitio es liviano, rápido y funciona bien en el celular.
- **Consistencia:** un sistema de diseño simple (tipografía, espacios, componentes) para que las páginas nuevas mantengan el estilo.

## 6. Alcance de la v1

### Incluido

| # | Funcionalidad | Notas |
|---|---|---|
| 1 | **Inicio** | Titular, servicios destacados, proyectos en desarrollo y llamado a la acción. |
| 2 | **Servicios** | Qué hacemos y con qué rubros trabajamos. |
| 3 | **Proyectos con página de detalle** | **Trabajos anteriores** como casos reales (seguimiento de camiones recolectores y cálculo de medidas para una imprenta), con problema, solución, tecnologías y resultado. **Proyectos en desarrollo** (barbería y gestión de tareas), rotulados como tales, con problema, solución propuesta, tecnologías y estado; pasan a "caso" cuando estén terminados. |
| 4 | **Quiénes somos** | Equipo y presentación, incluida la mención a la UTN FRC. |
| 5 | **Contacto** | Botón principal a WhatsApp y formulario como alternativa. |
| 6 | **Formulario de contacto** | Guarda la consulta en la base de datos y avisa por mail a los dos. Si el mail falla, la consulta no se pierde. Pide solo los datos mínimos e informa el tiempo de respuesta y el aviso de privacidad. |
| 7 | **Estadísticas de visitas** | Analítica básica y liviana. |

### Acción principal (llamado a la acción)

- **Principal:** escribir por **WhatsApp**. Es el botón destacado en todo el sitio.
- **Secundaria:** completar el formulario de contacto.
- **Compromiso de respuesta:** **24 horas hábiles**, informado junto al formulario y al botón de WhatsApp.

### Fuera de la v1

- Área de clientes, login y portal de acceso.
- Panel de administración: el contenido se carga desde el código o con migraciones.
- Versión en inglés: el sitio sale solo en español.
- App nativa. La API REST se diseña pensando en ella, pero la app no se construye ahora.

### Sin decidir (no bloquea la v1)

- Blog o sección de novedades.

## 7. Restricciones

- **Equipo:** dos personas, en paralelo con la facultad.
- **Plazo:** sin fecha fija. Se avanza según el tiempo disponible.
- **Presupuesto:** hasta **USD 10 por mes** entre dominio y hosting.
- **Stack:** Java 21, Spring Boot, Thymeleaf, Tailwind, PostgreSQL y Flyway (ver `CLAUDE.md`).
- **Diseño:** las páginas se bocetan en Figma antes de codificar.

## 8. Supuestos

- Los visitantes llegan sobre todo desde el celular, por enlaces de WhatsApp, LinkedIn o búsquedas por el nombre de la marca.
- Los trabajos anteriores, más los proyectos en desarrollo bien presentados, alcanzan como prueba de capacidad para la v1.
- Podemos responder las consultas en 24 horas hábiles.

## 9. Riesgos

| Riesgo | Impacto | Mitigación |
|---|---|---|
| Los proyectos "en desarrollo" hacen que el sitio parezca poco terminado. | Alto | Presentarlos con buen diseño, foco en el problema y la solución, capturas o maquetas reales y un estado claro. Actualizarlos seguido. |
| Sin fecha fija, el proyecto se estira indefinidamente. | Medio | Definir hitos chicos, por ejemplo en este orden: Figma, Inicio, Contacto y publicación. |
| Spam en el formulario. | Bajo | Protección liviana, como un campo trampa (honeypot) y límite de envíos. |
| La analítica pone lento el sitio o pide aviso de cookies. | Bajo | Usar una herramienta liviana que no use cookies. |

## 10. Criterios de éxito

A los **3 meses de publicada** la v1:

- [ ] Llega **al menos 1 consulta real por mes** por el sitio, de alguien que no nos conocía.
- [ ] **Lo usamos como carta de presentación:** lo mandamos a contactos con confianza, porque se ve profesional.
- [ ] **Aparece en Google por la marca:** buscar "CHI2" junto al rubro lleva al sitio.

### Criterio de "v1 terminada" (para publicar)

- [ ] Todas las páginas de la sección 6 publicadas, con contenido real y sin textos de relleno.
- [ ] El formulario guarda la consulta y llega el mail. Probado de punta a punta en producción.
- [ ] El botón de WhatsApp funciona en celular y en computadora.
- [ ] El dominio propio está configurado, con HTTPS.
- [ ] La analítica está activa.

## 11. Pendientes de este documento

- [ ] Titular o mensaje principal (ver `CLAUDE.md` §2).
- [ ] Nombre del dominio.
- [ ] Herramienta de analítica (por ejemplo Plausible, Umami o GoatCounter) y su costo dentro del presupuesto.
- [ ] Decidir si el blog entra en alguna versión.

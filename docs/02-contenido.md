# Contenido del sitio · Web principal CHI2

**Estado:** borrador v0.1
**Última actualización:** 2026-11-18
**Referencia:** `docs/01-vision-alcance.md`

Textos de cada página, listos para pasar a las plantillas. Cuando hay variantes, se elige una y se borran las demás.

**Tono:** cercano y claro, con voseo y sin tecnicismos. Hablamos de los problemas y resultados del negocio, no de tecnologías. Nada de promesas que no podamos sostener.

---

## 1. Mensaje central

Es la idea que el visitante tiene que llevarse, aunque solo lea la primera pantalla:

> Antes de programar, entendemos cómo trabaja tu negocio. Después construimos la herramienta que necesitás, ni más ni menos.

**Diferenciales**, en este orden:
1. **Analizamos el negocio antes de programar.** Relevamos procesos y flujos de trabajo, que es nuestra formación y experiencia.
2. **Hecho a medida.** El sistema se adapta a tu forma de trabajar, no al revés.
3. **Trato cercano.** Escuchamos, entendemos la necesidad y acompañamos.

---

## 2. Inicio

### Titular

> **Entendemos tu negocio y construimos la herramienta que necesita.**

### Mensaje de apoyo

> Desarrollamos sistemas para barberías, peluquerías y negocios chicos de toda Argentina. Primero analizamos cómo trabajás y después construimos una herramienta pensada para tu día a día.

### Servicios destacados (las dos tarjetas)

**Sistemas a medida**
> Turnos, clientes, tareas o lo que tu negocio necesite organizar, en una herramienta hecha para tu forma de trabajar.

**Análisis de tu negocio** *(propuesta: convierte el diferencial en un servicio)*
> Antes de programar, relevamos tus procesos y te mostramos dónde se pierde tiempo y qué conviene mejorar.

---

## 3. Servicios

### Introducción

> Hacemos sistemas a medida para negocios que hoy se organizan con cuadernos, planillas o grupos de WhatsApp. No partimos de una aplicación genérica: empezamos por entender cómo trabajás y diseñamos la herramienta a partir de eso.

### Cómo trabajamos

1. **Charlamos.** Nos contás qué te pasa y qué querés mejorar. La primera charla es gratis.
2. **Relevamos.** Vemos cómo trabaja tu negocio hoy: procesos, tiempos, quién hace qué.
3. **Te proponemos.** Te mostramos qué haríamos, en qué orden y cuánto llevaría.
4. **Construimos y te mostramos avances.** Vas viendo el sistema mientras lo hacemos y opinás.
5. **Lo ponemos en marcha y te acompañamos.** Te ayudamos a empezar a usarlo y te damos soporte después de la entrega.

---

## 4. Proyectos

Son los proyectos "en desarrollo" de la v1: barbería y gestión de tareas. Cada uno vive en su propio repo
(`chi2-peluqueria` y `chi2-gestor_tareas`); estos textos salen de sus documentos de visión y se actualizan
cuando avanzan. En el sitio están en `CatalogoProyectos`.

#### Sistema para barberías

- **Rubro:** barberías y peluquerías.
- **Resumen:** Turnos online, la agenda de cada barbero y el control de lo que se cobra, todo desde el celular.
- **Problema:** Las barberías arreglan los turnos por WhatsApp, a mano: el dueño o los barberos contestan mensajes y anotan en un cuaderno o en el celular. Se pisan turnos, se pierden mensajes y nadie sabe con certeza cuántos cortes hizo cada barbero ni cuánto se cobró en efectivo y cuánto por transferencia.
- **Solución:** Cada barbería tiene su link de reservas: el cliente elige el servicio, el barbero (o "cualquiera"), el día y un horario libre, y la reserva le llega a la barbería por WhatsApp, sin costo. Cada barbero ve su agenda y marca cada turno como empezado y terminado; el dueño ve la de todos. Al terminar se anota cuánto se cobró y cómo, y cada uno ve sus números del día, la semana y el mes. Está pensado para usarse desde el celular, sin instalar nada desde una tienda de apps.
- **Tecnologías:** Java, Spring Boot, PostgreSQL, Tailwind CSS.
- **Estado actual:** Ya funcionan el alta de cada barbería con sus usuarios, el catálogo de servicios, el perfil y los horarios de cada barbero (con francos y ausencias) y la agenda de turnos sin superposiciones. Siguen la atención de cada turno con el registro del cobro, las métricas por barbero y del negocio, y la reserva online con aviso por WhatsApp.

#### Gestión de tareas

- **Rubro:** organización personal y de equipos.
- **Resumen:** Lo de hoy, la semana, el mes y el largo plazo en un solo lugar, con prioridades y tablero.
- **Problema:** Las tareas quedan repartidas entre notas del celular, papelitos, chats y la memoria. Cuesta ver qué hay que hacer hoy, qué se viene en la semana y qué quedó sin planificar, y lo urgente se mezcla con lo que puede esperar. Las herramientas conocidas tienen tantas opciones que muchas veces terminan sin usarse.
- **Solución:** Al abrirlo muestra lo de hoy, ordenado por prioridad y con lo atrasado a la vista. Una tarea se carga en segundos: se escribe y se guarda con Enter, con atajos para la prioridad y el día. La semana y el mes se ven como calendario, lista o tablero, donde las tareas se arrastran de pendiente a en curso y a finalizada; lo que todavía no tiene fecha queda en "Por planificar". Todo se guarda al instante, sin botón de guardar.
- **Tecnologías:** Java, Spring Boot, PostgreSQL, JavaScript, Tailwind CSS.
- **Estado actual:** La primera etapa, de uso personal, ya funciona: vistas de hoy, semana, mes y largo plazo, carga rápida, tablero y edición al instante. Sigue la etapa de grupos, con tareas compartidas y roles, que se va a probar primero con un equipo de tesis de la facultad. Después, adaptarlo como producto para negocios.

### Trabajos anteriores (casos para la v1)

Van como casos, con problema, solución, tecnologías y resultado. Son la prueba de capacidad más fuerte del sitio.

- **Seguimiento de camiones recolectores de residuos:** prototipo para la ciudad de Santiago del Estero.
- **Cálculo de medidas para una imprenta.**

> [PENDIENTE (Daniel): detallar ambos y definir si se muestran con el nombre del cliente o anonimizados]

---

## 5. Quiénes somos

### Introducción

> Somos Nahuel y Daniel, estudiantes de último año de Ingeniería en Sistemas de Información en la UTN FRC. Nos conocimos en la facultad en 2021, hicimos juntos la mayoría de las materias y de ahí nació la amistad y la idea de trabajar juntos algún día. CHI2 es ese proyecto en marcha: soluciones digitales para problemas reales de negocios reales, que es lo que más nos apasiona.

### Qué nos importa

> Nuestro objetivo es que tu trabajo de todos los días sea más simple. Por eso escuchamos primero, nos tomamos el tiempo de entender tu negocio y construimos una herramienta que realmente uses.

### Equipo

**Nahuel Luna** · Análisis funcional y desarrollo
> Desde mayo de 2026 trabaja como analista funcional en EPEC, donde releva procesos y flujos de trabajo para diseñar soluciones tecnológicas.

**Daniel Patolsky** · [PENDIENTE: rol]
> Desarrolló un prototipo de seguimiento de camiones recolectores para la ciudad de Santiago del Estero y un sistema de cálculo de medidas para una imprenta. [PENDIENTE: que Daniel lo complete y lo confirme]

---

## 6. Contacto

### Formulario (propuesta)

| Campo | Obligatorio |
|---|---|
| Nombre | Sí |
| WhatsApp o mail | Sí |
| Rubro del negocio | No |
| Contanos qué necesitás | Sí |

**Texto junto al botón:** "Respondemos en 24 horas hábiles. Usamos tus datos solo para responderte y los borramos a los 3 meses."

El detalle completo del formulario (validaciones, mensajes de error y de éxito) está en `docs/03-formulario-contacto.md`.

---

## 7. Textos de diseño (propuesta)

Frases cortas que sumó el rediseño visual para dar jerarquía a cada página. Son propuesta: si no convencen, se cambian acá y en la plantilla.

| Dónde | Texto |
|---|---|
| Inicio, rótulo sobre el titular | Sistemas a medida · Argentina |
| Servicios, título | Empezamos por entender cómo trabajás |
| Proyectos, título | Lo que hicimos y lo que estamos construyendo |
| Quiénes somos, título | Dos amigos de la facultad, un proyecto en marcha |
| Contacto, título | Hablemos. |
| Bloque de cierre (todas las páginas) | ¿Querés ordenar tu negocio? · Contanos cómo trabajás hoy y vemos juntos qué te conviene. La primera charla es gratis. |

## 8. Pendientes

Se siguen en `PENDIENTES.md`.

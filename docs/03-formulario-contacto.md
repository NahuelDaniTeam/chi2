# Especificación · Formulario de contacto

**Estado:** borrador v0.1
**Última actualización:** 2026-11-18
**Referencias:** `docs/01-vision-alcance.md` §6 · `docs/02-contenido.md` §6 · `PENDIENTES.md` #15

Este documento define cómo funciona el formulario de contacto, para que quien lo programe no tenga que adivinar nada. Cada criterio de aceptación de la §10 se convierte en, al menos, un test.

---

## 1. Objetivo

El formulario es la **acción secundaria** del sitio; la principal es WhatsApp. Sirve para quien prefiere no escribir por chat. Una consulta enviada **nunca se pierde**: se guarda primero y el aviso por mail se reintenta hasta que sale.

## 2. Decisiones tomadas

| Tema | Decisión |
|---|---|
| Dónde se guarda | Tabla `consulta` en PostgreSQL, creada por una migración Flyway. |
| A quién se avisa | Al **mail personal de cada uno** (Nahuel y Daniel), con una lista configurable de destinatarios. |
| Cómo se envía | **Gmail SMTP** con contraseña de aplicación, configurable por variables de entorno. Cambiar de proveedor después es solo configuración. |
| Si el mail falla | La consulta queda guardada con aviso **pendiente** y una tarea programada reintenta el envío. |
| Respuesta al visitante | **Solo un mensaje en pantalla**, sin mail automático, para que nadie use el formulario para mandar spam a terceros. |
| Retención | Las consultas se borran a los **3 meses**, con una tarea automática diaria. |
| Anti-spam | Campo trampa (honeypot) y límite de envíos por IP. |
| Datos que **no** se guardan | La IP del visitante. Se usa solo en memoria para el límite de envíos. |

## 3. Campos

Es un solo formulario con 4 campos visibles y 1 oculto.

| Campo | Nombre técnico | Obligatorio | Reglas | Mensaje de error |
|---|---|---|---|---|
| Nombre | `nombre` | Sí | De 2 a 80 caracteres, sin espacios al inicio ni al final. | "Contanos cómo te llamás." |
| WhatsApp o mail | `contacto` | Sí | Un mail válido **o** un teléfono (ver §3.1). Hasta 120 caracteres. | "Dejanos un WhatsApp o un mail válido para responderte." |
| Rubro del negocio | `rubro` | No | Hasta 80 caracteres. Texto libre, con sugerencias: Barbería, Peluquería y Otro. | — |
| Contanos qué necesitás | `mensaje` | Sí | De 10 a 2000 caracteres. | "Contanos un poco más (mínimo 10 caracteres)." |
| *(trampa, oculto)* | `sitio_web` | — | Tiene que llegar vacío (ver §6). | — |

### 3.1. Cómo se interpreta "WhatsApp o mail"

- **Si tiene `@`**, se valida como mail y se guarda con `tipo_contacto = MAIL`.
- **Si no**, se le quitan espacios, guiones, paréntesis y el `+`. Lo que queda tiene que ser solo dígitos, entre 8 y 15, y se guarda con `tipo_contacto = WHATSAPP`.
  - Se guarda **tal como lo escribió el visitante**, sin normalizar. Así el aviso muestra exactamente lo que puso.
  - Para el link de respuesta (§7) se usa la versión sin símbolos. Si no empieza con `54`, se le antepone `549`. Puede fallar en casos raros; se acepta, porque el número igual se ve en el aviso.

## 4. Flujo

```
Visitante envía el formulario
  │
  ├─ ¿El campo trampa vino lleno? ── sí ─▶ responde "éxito" y no guarda nada (§6)
  │
  ├─ ¿La IP superó el límite? ─────── sí ─▶ 429 · "Recibimos muchas consultas desde tu conexión..."
  │
  ├─ ¿La validación falla? ────────── sí ─▶ vuelve el formulario con errores y lo escrito (§8)
  │
  ▼
Guarda la consulta (estado_aviso = PENDIENTE)   ◀── a partir de acá la consulta no se pierde
  │
  ▼
Intenta mandar el aviso a los destinatarios, fuera de la transacción y con timeouts cortos
  ├─ sale bien ─▶ estado_aviso = ENVIADO, avisada_en = ahora
  └─ falla ─────▶ queda PENDIENTE, intentos_aviso + 1 y ultimo_error = mensaje
  │
  ▼
Visitante ve: "¡Listo! Recibimos tu consulta. Respondemos en 24 horas hábiles."
```

**El visitante ve éxito aunque el mail falle.** La consulta ya está guardada y el reintento es asunto nuestro.

### 4.1. Reintentos

- Una tarea programada (`@Scheduled`) corre **cada 5 minutos** y toma las consultas con `estado_aviso = PENDIENTE`.
- Por cada una intenta el envío y actualiza `intentos_aviso` y `ultimo_error`.
- Al llegar a **10 intentos fallidos** la marca como `FALLIDO` y deja un log de nivel `ERROR` con el id de la consulta. Así se puede revisar a mano, sin reintentar para siempre.

### 4.2. Retención

- Una tarea programada **diaria** (por ejemplo, a las 4:00 UTC) borra las consultas con `creada_en` de más de 3 meses, cualquiera sea su estado.
- El plazo es configurable (`chi2.consultas.retencion`, por defecto `P3M`).

## 5. Endpoints

Las dos entradas usan el mismo servicio (`ConsultaService`), así que la lógica no se duplica. Esto cumple la decisión de tener API REST desde el inicio (`CLAUDE.md` §8).

### 5.1. Formulario web (Thymeleaf), `POST /contacto`

- Funciona **sin JavaScript**: es un formulario HTML común.
- Si sale bien: redirige a `GET /contacto?enviada` (patrón Post/Redirect/Get), para que al recargar no se reenvíe. La página muestra el mensaje de éxito en lugar del formulario.
- Si hay errores: vuelve a mostrar `/contacto` con los errores al lado de cada campo y conserva lo escrito.
- Si se superó el límite: muestra el mensaje de límite arriba del formulario, con HTTP 429.

### 5.2. API REST, `POST /api/consultas`

Pensada para la app nativa futura.

**Pedido**
```json
{ "nombre": "Ana", "contacto": "351 555-1234", "rubro": "Barbería", "mensaje": "Quiero ordenar los turnos..." }
```

**Respuestas**

| Caso | HTTP | Cuerpo |
|---|---|---|
| Recibida, o campo trampa lleno | `201 Created` | `{ "mensaje": "Recibimos tu consulta. Respondemos en 24 horas hábiles." }` |
| Validación | `400 Bad Request` | `ProblemDetail` con `errores: { "<campo>": "<mensaje>" }` |
| Límite superado | `429 Too Many Requests` | `ProblemDetail` con el mensaje de límite |

- No devuelve el id ni ningún dato guardado.
- En JSON, el campo trampa es `sitio_web`, igual que en el formulario.

## 6. Anti-spam

- **Campo trampa:** un input `sitio_web` que el visitante no ve. Se oculta con CSS fuera de pantalla, no con `type="hidden"`, y lleva `tabindex="-1"` y `autocomplete="off"`. Si llega con contenido, se responde **como si hubiera salido bien** pero no se guarda nada, para no darle pistas al bot.
- **Límite por IP:** **5 consultas por hora por IP**, contando las de las dos entradas juntas.
  - Se guarda **en memoria**: un mapa de IP a marcas de tiempo, con limpieza periódica. No se persiste nada.
  - Si se reinicia la app, los contadores vuelven a cero. Alcanza para la v1, que corre en una sola instancia.
  - Detrás del proxy del hosting, la IP real sale de `X-Forwarded-For`. Para eso se configura `server.forward-headers-strategy=framework`.
  - El límite es configurable: `chi2.consultas.limite-por-hora`.
- **Sin captcha en la v1.** Si llega spam igual, el siguiente paso es Cloudflare Turnstile, que es gratis y no le pide nada al visitante.

## 7. El aviso por mail

- **De:** la cuenta Gmail remitente (`spring.mail.username`).
- **Para:** cada destinatario de `chi2.consultas.destinatarios`.
- **Responder a (`Reply-To`):** el mail del visitante, si dejó uno. Así se le contesta con "Responder".
- **Asunto:** `Nueva consulta: <nombre> (<rubro>)`. Si no hay rubro, va sin el paréntesis.
- **Cuerpo**, en texto plano:

```
Nueva consulta desde el sitio.

Nombre:   Ana
Contacto: 351 555-1234 (WhatsApp)
Rubro:    Barbería
Fecha:    18/11/2026 15:42 (hora Argentina)

Mensaje:
Quiero ordenar los turnos...

Responder por WhatsApp: https://wa.me/5493515551234
```

- La fecha se guarda en UTC y se muestra en hora argentina (`America/Argentina/Cordoba`), como pide `CLAUDE.md` §8.
- La línea "Responder por WhatsApp" aparece solo si el contacto es de tipo WhatsApp.

## 8. Interfaz

- **Ubicación:** la tarjeta "O dejanos tu consulta" de `contacto.html`, que reemplaza al recuadro `[PENDIENTE]` actual.
- **Etiquetas visibles** en cada campo, nada de usar solo el placeholder.
- **Atributos `autocomplete`:** `name` en nombre, `email` en contacto y `organization-title` en rubro (o `off`).
- **Errores:** debajo de cada campo y asociados con `aria-describedby`. Al volver con errores, el foco va al primer campo con error.
- **Botón:** "Enviar consulta". Mientras se envía, se deshabilita para evitar el doble clic. Esto es una mejora con JavaScript y no hace falta para que el formulario funcione.
- **Debajo del botón:** "Respondemos en 24 horas hábiles. Usamos tus datos solo para responderte y los borramos a los 3 meses."
- **Éxito:** la tarjeta muestra "¡Listo, <nombre>! Recibimos tu consulta. Respondemos en 24 horas hábiles." y repite el botón de WhatsApp por si es urgente.
- Diseñado primero para celular.

## 9. Datos

### 9.1. Migración `V2__consulta.sql`

```sql
CREATE TABLE consulta (
    id              BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    nombre          VARCHAR(80)   NOT NULL,
    contacto        VARCHAR(120)  NOT NULL,
    tipo_contacto   VARCHAR(10)   NOT NULL CHECK (tipo_contacto IN ('MAIL', 'WHATSAPP')),
    rubro           VARCHAR(80),
    mensaje         VARCHAR(2000) NOT NULL,
    estado_aviso    VARCHAR(10)   NOT NULL DEFAULT 'PENDIENTE'
                    CHECK (estado_aviso IN ('PENDIENTE', 'ENVIADO', 'FALLIDO')),
    intentos_aviso  INT           NOT NULL DEFAULT 0,
    ultimo_error    VARCHAR(500),
    creada_en       TIMESTAMPTZ   NOT NULL,
    avisada_en      TIMESTAMPTZ
);

CREATE INDEX consulta_pendientes_idx ON consulta (estado_aviso) WHERE estado_aviso = 'PENDIENTE';
CREATE INDEX consulta_creada_en_idx  ON consulta (creada_en);
```

Si al momento de programarlo ya existe otra `V2` en `main`, se renumera según la regla de `CLAUDE.md` §8.

### 9.2. Configuración

| Propiedad | Variable de entorno | Valor |
|---|---|---|
| `spring.mail.host` | — | `smtp.gmail.com` |
| `spring.mail.port` | — | `587`, con STARTTLS |
| `spring.mail.username` | `CHI2_MAIL_USUARIO` | Cuenta Gmail remitente |
| `spring.mail.password` | `CHI2_MAIL_CLAVE` | Contraseña de aplicación de Gmail, **nunca en el repo** |
| `spring.mail.properties.mail.smtp.connectiontimeout` / `timeout` / `writetimeout` | — | `5000` ms |
| `chi2.consultas.destinatarios` | `CHI2_CONSULTAS_DESTINATARIOS` | Mails separados por coma, **nunca en el repo** |
| `chi2.consultas.limite-por-hora` | — | `5` |
| `chi2.consultas.retencion` | — | `P3M` |

- **Dependencia nueva:** `spring-boot-starter-mail`. Además, hay que agregar `@EnableScheduling`.
- **Desarrollo local:** sumar **Mailpit** a `compose.yaml`. Atrapa los mails y los muestra en `http://localhost:8025`, así nadie manda mails reales mientras programa.
- **Sin destinatarios configurados:** la app arranca igual, pero el aviso queda `PENDIENTE` y se deja un log `WARN`. Así se puede programar sin tener todo configurado.

## 10. Criterios de aceptación

Cada uno lleva al menos un test. Los que tocan la base usan `@SpringBootTest` con Testcontainers; los de controladores, `@WebMvcTest`.

**Envío correcto**
1. Con datos válidos, `POST /contacto` guarda la consulta y redirige a `/contacto?enviada`, que muestra el mensaje de éxito.
2. Con datos válidos, `POST /api/consultas` responde `201` y guarda la consulta.
3. Al guardar, se manda un aviso a cada destinatario con el asunto, el cuerpo y el `Reply-To` de la §7.

**Validación**
4. Si falta un campo obligatorio o no cumple sus reglas, no se guarda nada. El formulario vuelve con el error en ese campo y conserva lo escrito; la API responde `400` con el error por campo.
5. `contacto` acepta `ana@mail.com`, `+54 9 351 555-1234` y `3515551234`, y rechaza `hola`, `123` y `ana@`.

**Si el mail falla**
6. Si el servidor de mail falla, la consulta queda guardada como `PENDIENTE`, con `intentos_aviso = 1`, y el visitante ve el mensaje de éxito.
7. La tarea de reintento manda las pendientes y las marca `ENVIADO`.
8. Al décimo intento fallido, la consulta pasa a `FALLIDO` y queda un log de nivel `ERROR`.

**Anti-spam**
9. Si el campo trampa viene lleno, se responde éxito y **no** se guarda nada ni se manda mail.
10. El sexto envío de la misma IP dentro de una hora recibe `429`; uno desde otra IP pasa.

**Retención**
11. La tarea de retención borra las consultas de más de 3 meses y deja las más nuevas.

**Interfaz**
12. Sin JavaScript, el formulario funciona de punta a punta.

## 11. Fuera de alcance (v1)

- Panel para ver las consultas. Se ven en los mails y, si hace falta, en la base.
- Mail de confirmación al visitante.
- Captcha (ver §6).
- Adjuntar archivos.

## 12. Pendientes que bloquean la puesta en producción

No bloquean programar el formulario, que se prueba con Mailpit. Sí bloquean publicarlo. Están en `PENDIENTES.md`.

- La cuenta Gmail remitente y su contraseña de aplicación.
- Los mails de destino de cada uno, cargados como variable de entorno en el hosting.
- **A verificar:** si la Ley 25.326 obliga a inscribir la base de consultas ante la Agencia de Acceso a la Información Pública (AAIP), y qué texto mínimo tiene que tener el aviso de privacidad (finalidad, plazo y cómo pedir que se borren los datos).

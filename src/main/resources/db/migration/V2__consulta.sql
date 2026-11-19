-- Consultas del formulario de contacto (docs/03-formulario-contacto.md §9.1)
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

create table tb_cliente
(
	tb_cliente_id serial not null,
	tb_cliente_tipdocide character(1) not null,
	-- Puede ser:
	-- 0(DTNDSR	- Documento Tributario de No Domiciliado sin RUC)
	-- 1(DNI  	- Documento Nacional de Identidad)
	-- 4(CE   	- Carnet de Extranjería)
	-- 6(RUC  	- Registro Único de Contribuyentes)
	-- 7(PAS  	- Pasaporte)
	-- A(CDI  	- Cédula Diplomática de Identidad)
	-- B(DIPRND	- DOC.IDENT.PAIS.RESIDENCIA-NO.D)
	-- C(TIN	- Tax Identification Number - TIN – Doc Trib PP.NN)
	-- D(IN		- Identification Number - IN – Doc Trib PP. JJ)
	-- E(TAM	- TAM- Tarjeta Andina de Migración)
	tb_cliente_numdocide character varying(15) not null,
	tb_cliente_razsoc character varying(50) not null,
	tb_cliente_dir character varying(50) not null,
	tb_cliente_tel character(9) null,
	constraint pk_cliente primary key(tb_cliente_id),
	constraint unq_cliente_tipdocidenumdocide unique(tb_cliente_tipdocide,tb_cliente_numdocide),
	constraint chk_cliente_id check(tb_cliente_id > 0),
	constraint chk_cliente_tipdociden check(tb_cliente_tipdocide in ('0','1','4','6','7','A','B','C','D','E')),
	constraint chk_cliente_tel check(tb_cliente_tel similar to '[0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9]')
);

create table tb_rol
(
	tb_rol_id serial not null,
	tb_rol_nom character varying(50) not null,
	constraint pk_rol primary key(tb_rol_id),
	constraint unq_rol_nom unique(tb_rol_nom),
	constraint chk_rol_id check(tb_rol_id > 0)
);

create table tb_usuario
(
	tb_usuario_id serial not null,
	tb_usuario_tipdocide character(1) not null,
	-- Puede ser:
	-- 1(DNI  	- Documento Nacional de Identidad)
	-- 4(CE   	- Carnet de Extranjería)
	-- 7(PAS  	- Pasaporte)
	-- E(TAM	- TAM- Tarjeta Andina de Migración)
	tb_usuario_numdocide character varying(15) not null,
	tb_usuario_apepat character varying(50) not null,
	tb_usuario_apemat character varying(50) not null,
	tb_usuario_nom character varying(50) not null,
	tb_usuario_corele character varying(50) not null,
	tb_usuario_con character(6) not null,
	tb_usuario_act boolean not null,
	tb_usuario_tip character(1) not null, -- Puede ser: C(Colaborador del Cliente), E(Colaborador de la Empresa), S(Gestor de Soporte)
	tb_cliente_id integer null,
	tb_rol_id integer null,
	constraint pk_usuario primary key(tb_usuario_id),
	constraint fk_cliente_usuario foreign key(tb_cliente_id) references tb_cliente(tb_cliente_id),
	constraint fk_rol_usuario foreign key(tb_rol_id) references tb_rol(tb_rol_id),
	constraint chk_usuario_id check(tb_usuario_id > 0),
	constraint chk_usuario_tipdociden check(tb_usuario_tipdocide in ('1','4','7','E')),
	constraint chk_usuario_con check(tb_usuario_con similar to '[0-9][0-9][0-9][0-9][0-9][0-9]'),
	constraint chk_usuario_tip check(tb_usuario_tip in ('C','E','S'))
);

create table tb_solicitud
(
	tb_solicitud_id serial not null,
	tb_solicitud_ani integer not null,
	tb_solicitud_mes integer not null,
	tb_solicitud_numcor integer not null,
	tb_solicitud_fechor timestamp not null default now(),
	tb_solicitud_fechorasi timestamp null,
	tb_solicitud_fechoriniate timestamp null,
	tb_solicitud_fechorterate timestamp null,
	tb_solicitud_mot text not null,
	tb_solicitud_est character(1) not null, -- Puede ser: P(Pendiente), A(Asignada), R(Proceso), T(Atendida)
	tb_solicitud_tip character(1) not null, -- Puede ser: E(Error), C(Capacitación), R(Requerimiento)
	tb_usuario_id integer not null,
	constraint pk_solicitud primary key(tb_solicitud_id),
	constraint fk_usuario_solicitud foreign key(tb_usuario_id) references tb_usuario(tb_usuario_id),
	constraint unq_solicitud_animesnumcor unique(tb_solicitud_ani,tb_solicitud_mes,tb_solicitud_numcor),
	constraint chk_solicitud_ani check(tb_solicitud_ani > 0),
	constraint chk_solicitud_mes check(tb_solicitud_mes between 1 and 12),
	constraint chk_solicitud_numcor check(tb_solicitud_numcor > 0),
	constraint chk_solicitud_fechor check(tb_solicitud_fechor <= now()),
	constraint chk_solicitud_fechorasi check(tb_solicitud_fechorasi <= now()),
	constraint chk_solicitud_fechoriniate check(tb_solicitud_fechoriniate <= now()),
	constraint chk_solicitud_fechorterate check(tb_solicitud_fechorterate <= now()),
	constraint chk_solicitud_fechorasifechor check(tb_solicitud_fechorasi >= tb_solicitud_fechor),
	constraint chk_solicitud_fechoriniatefechorasi check(tb_solicitud_fechoriniate >= tb_solicitud_fechorasi),
	constraint chk_solicitud_fechorteratefechoriniate check(tb_solicitud_fechorterate >= tb_solicitud_fechoriniate),	
	constraint chk_solicitud_est check(tb_solicitud_est in ('P','A','R','T')),
	constraint chk_solicitud_tip check(tb_solicitud_tip in ('E','C','R'))
);

create table tb_asignacion
(
	tb_asignacion_fechor timestamp not null default now(),
	tb_asignacion_fechoriniate timestamp null,
	tb_asignacion_fechorterate timestamp null,
	tb_asignacion_coo boolean not null,
	tb_solicitud_id integer not null,
	tb_usuario_id integer not null,
	constraint pk_asignacion primary key(tb_solicitud_id,tb_usuario_id),
	constraint fk_solicitud_asignacion foreign key(tb_solicitud_id) references tb_solicitud(tb_solicitud_id),
	constraint fk_usuario_asignacion foreign key(tb_usuario_id) references tb_usuario(tb_usuario_id),
	constraint chk_asignacion_fechor check(tb_asignacion_fechor <= now())
);

create table tb_trabajo
(
	tb_trabajo_id serial not null,
	tb_trabajo_fechorini timestamp not null default now(),
	tb_trabajo_fechorter timestamp null,
	tb_trabajo_des text not null,
	tb_solicitud_id integer not null,
	tb_usuario_id integer not null,
	constraint pk_trabajo primary key(tb_trabajo_id),
	constraint fk_asignacion_trabajo foreign key(tb_solicitud_id,tb_usuario_id) references tb_asignacion(tb_solicitud_id,tb_usuario_id),
	constraint chk_trabajo_id check(tb_trabajo_id > 0),
	constraint chk_trabajo_fechorini check(tb_trabajo_fechorini <= now()),
	constraint chk_trabajo_fechorter check(tb_trabajo_fechorter <= now()),
	constraint chk_trabajo_fechorterfechorini check(tb_trabajo_fechorter >= tb_trabajo_fechorini)
);


CREATE OR REPLACE FUNCTION sp_insertar_cliente(
    p_tipdocide CHAR(1),          -- Tipo de documento de identidad
    p_numdocide VARCHAR(15),      -- Número de documento de identidad
    p_apepat VARCHAR,             -- Apellido paterno
    p_apemat VARCHAR,             -- Apellido materno
    p_nombre VARCHAR,             -- Nombre del cliente
    p_razon_social VARCHAR,       -- Razón social
    p_direccion VARCHAR,          -- Dirección
    p_telefono CHAR(9),           -- Teléfono
    p_corele VARCHAR,             -- Correo electrónico
    p_con CHAR(6),                -- Contraseña
    p_tb_rol_id INT               -- Rol del usuario
)
RETURNS VARCHAR AS $$
DECLARE
    cResult VARCHAR(100);
    v_tb_cliente_id INT;
    v_tb_usuario_id INT;
BEGIN

    IF EXISTS (SELECT 1 FROM tb_usuario WHERE tb_usuario_corele = p_corele) THEN
        cResult := 'El correo ' || p_corele || ' ya se encuentra registrado.';
        RETURN cResult;
    END IF;


    INSERT INTO tb_cliente (
        tb_cliente_tipdocide, 
        tb_cliente_numdocide, 
        tb_cliente_razsoc, 
        tb_cliente_dir, 
        tb_cliente_tel
    )
    VALUES (
        p_tipdocide, 
        p_numdocide, 
        p_razon_social, 
        p_direccion, 
        p_telefono
    )
    RETURNING tb_cliente_id INTO v_tb_cliente_id;

    INSERT INTO tb_usuario (
        tb_usuario_tipdocide,
        tb_usuario_numdocide,
        tb_usuario_apepat,
        tb_usuario_apemat,
        tb_usuario_nom,
        tb_usuario_corele,
        tb_usuario_con,
        tb_usuario_act,
        tb_usuario_tip,
        tb_cliente_id,
        tb_rol_id
    )
    VALUES (
        '1', 
        p_numdocide, 
        p_apepat, 
        p_apemat, 
        p_nombre, 
        p_corele, 
        p_con, 
        TRUE,                 
        'C', 
        v_tb_cliente_id, 
        p_tb_rol_id
    )
    RETURNING tb_usuario_id INTO v_tb_usuario_id;

    cResult := 'OK';
    RETURN cResult;

EXCEPTION
    WHEN OTHERS THEN
        RETURN 'Error: ' || SQLERRM;
END;
$$ LANGUAGE plpgsql;



CREATE OR REPLACE FUNCTION SP_Auth_Login(cCorreo VARCHAR, cPassword VARCHAR)
RETURNS TABLE (
    idRol INT,
    nombreRol VARCHAR,
    idUsuario INT,
    correo VARCHAR,
    password CHAR(6),
    estado BOOLEAN,
    id INT,
    nombre VARCHAR,
	tipoUsuario CHAR(1)
) AS $$
BEGIN
    RETURN QUERY 
    WITH RECURSIVE t AS (
        -- Colaboradores asociados a clientes
        SELECT 
            u.tb_usuario_id AS idUsuario, 
            u.tb_cliente_id AS id,
            CONCAT(u.tb_usuario_nom, ' ', u.tb_usuario_apepat, ' ', u.tb_usuario_apemat)::VARCHAR AS nombre
        FROM tb_usuario u
        WHERE u.tb_usuario_tip = 'C' -- Colaborador del Cliente

        UNION ALL

        -- Colaboradores de la empresa
        SELECT 
            u.tb_usuario_id AS idUsuario, 
            NULL AS id,
            CONCAT(u.tb_usuario_nom, ' ', u.tb_usuario_apepat, ' ', u.tb_usuario_apemat)::VARCHAR AS nombre
        FROM tb_usuario u
        WHERE u.tb_usuario_tip <> 'C' -- Colaborador de la Empresa
    )
    SELECT 
        r.tb_rol_id AS idRol, 
        r.tb_rol_nom AS nombreRol, 
        u.tb_usuario_id AS idUsuario, 
        u.tb_usuario_corele AS correo, 
        u.tb_usuario_con AS password, 
        u.tb_usuario_act AS estado, 
        t.id, 
        t.nombre,
		u.tb_usuario_tip AS tipoUsuario
    FROM t
    INNER JOIN tb_usuario u ON u.tb_usuario_id = t.idUsuario
    INNER JOIN tb_rol r ON r.tb_rol_id = u.tb_rol_id
    WHERE u.tb_usuario_corele = cCorreo AND u.tb_usuario_con = cPassword;
END;
$$ LANGUAGE plpgsql;



CREATE OR REPLACE FUNCTION sp_insertar_usuario(
    p_tipdocide CHARACTER(1),
    p_numdocide VARCHAR(15),
    p_apepat VARCHAR(50),
    p_apemat VARCHAR(50),
    p_nombre VARCHAR(50),
    p_corele VARCHAR(50),
    p_con CHARACTER(6),
    p_act BOOLEAN,
    p_tip CHARACTER(1),
    p_rol_id INT DEFAULT NULL
)
RETURNS VARCHAR AS $$
DECLARE
    cResult VARCHAR(100);
BEGIN

    IF EXISTS (SELECT 1 FROM tb_usuario WHERE tb_usuario_corele = p_corele) THEN
        cResult := 'El correo ' || p_corele || ' ya se encuentra registrado.';
        RETURN cResult;
    END IF;

    INSERT INTO tb_usuario(
        tb_usuario_tipdocide, 
        tb_usuario_numdocide, 
        tb_usuario_apepat, 
        tb_usuario_apemat, 
        tb_usuario_nom, 
        tb_usuario_corele, 
        tb_usuario_con, 
        tb_usuario_act, 
        tb_usuario_tip, 
        tb_cliente_id, 
        tb_rol_id
    )
    VALUES (
        p_tipdocide, 
        p_numdocide, 
        p_apepat, 
        p_apemat, 
        p_nombre, 
        p_corele, 
        p_con, 
        p_act, 
        p_tip, 
        null, 
        p_rol_id
    );

    cResult := 'OK';
    RETURN cResult;

EXCEPTION
    WHEN OTHERS THEN
        RETURN 'Error: ' || SQLERRM;
END;
$$ LANGUAGE plpgsql;



CREATE OR REPLACE FUNCTION sp_editar_usuario(
    p_id_usuario INT,
    p_tipdocide CHARACTER(1),
    p_numdocide VARCHAR(15),
    p_apepat VARCHAR(50),
    p_apemat VARCHAR(50),
    p_nombre VARCHAR(50),
    p_corele VARCHAR(50),
    p_con CHARACTER(6),
    p_act BOOLEAN,
    p_tip CHARACTER(1),
    p_rol_id INT DEFAULT NULL
)
RETURNS VARCHAR AS $$
DECLARE
    cResult VARCHAR(100);
BEGIN

    IF EXISTS (SELECT 1 FROM tb_usuario WHERE tb_usuario_corele = p_corele AND tb_usuario_id <> p_id_usuario) THEN
        cResult := 'El correo ' || p_corele || ' ya se encuentra registrado para otro usuario.';
        RETURN cResult;
    END IF;

    UPDATE tb_usuario
    SET 
        tb_usuario_tipdocide = p_tipdocide,
        tb_usuario_numdocide = p_numdocide,
        tb_usuario_apepat = p_apepat,
        tb_usuario_apemat = p_apemat,
        tb_usuario_nom = p_nombre,
        tb_usuario_corele = p_corele,
        tb_usuario_con = p_con,
        tb_usuario_act = p_act,
        tb_usuario_tip = p_tip,
        tb_rol_id = p_rol_id
    WHERE tb_usuario_id = p_id_usuario;

    cResult := 'OK';
    RETURN cResult;

EXCEPTION
    WHEN OTHERS THEN
        RETURN 'Error: ' || SQLERRM;
END;
$$ LANGUAGE plpgsql;



CREATE OR REPLACE FUNCTION sp_insertar_asignacion(
    p_idSolicitud INT,  
    p_idUsuario INT,  
    p_esCoordinador BOOLEAN
) 
RETURNS TEXT AS $$
DECLARE
    cResult TEXT;
BEGIN
    -- Verificar si ya existe la asignación
    IF EXISTS (SELECT 1 FROM tb_asignacion 
               WHERE tb_usuario_id = p_idUsuario 
                 AND tb_solicitud_id = p_idSolicitud) THEN
        cResult := 'El usuario ya se encuentra asignado a la solicitud con ID ' || p_idSolicitud || '.';
        RETURN cResult;
    END IF;

    -- Actualizar la solicitud si está pendiente
    IF EXISTS (SELECT 1 FROM tb_solicitud 
               WHERE tb_solicitud_id = p_idSolicitud 
                 AND tb_solicitud_est = 'P') THEN
        UPDATE tb_solicitud 
        SET tb_solicitud_fechorasi = NOW(), tb_solicitud_est = 'A'
        WHERE tb_solicitud_id = p_idSolicitud;
    END IF;
 
    -- Insertar la nueva asignación
    INSERT INTO tb_asignacion (tb_solicitud_id, tb_usuario_id, tb_asignacion_coo) 
    VALUES (p_idSolicitud, p_idUsuario, p_esCoordinador); 
    
    -- Retornar resultado exitoso
    cResult := 'OK';
    RETURN cResult;

EXCEPTION
    WHEN OTHERS THEN
        RETURN 'Error: ' || SQLERRM;
END;
$$ LANGUAGE plpgsql;



CREATE OR REPLACE FUNCTION sp_asignar_coordinador(
    p_idSolicitud INT,  
    p_idUsuario INT,  
    p_esCoordinador BOOLEAN
) 
RETURNS TEXT AS $$
DECLARE
    cResult TEXT;
BEGIN
    
    UPDATE tb_asignacion SET tb_asignacion_coo = FALSE
	WHERE tb_solicitud_id = p_idSolicitud;
	
	UPDATE tb_asignacion SET tb_asignacion_coo = p_esCoordinador
	WHERE tb_solicitud_id = p_idSolicitud AND tb_usuario_id = p_idUsuario;
    
    cResult := 'OK';
    RETURN cResult;

EXCEPTION
    WHEN OTHERS THEN
        RETURN 'Error: ' || SQLERRM;
END;
$$ LANGUAGE plpgsql;



CREATE OR REPLACE FUNCTION sp_insertar_trabajo(
    p_descripcion TEXT,  
    p_idUsuario INT,  
    p_idSolicitud INT
) 
RETURNS TEXT AS $$
DECLARE
    cResult TEXT;
BEGIN
   	 IF EXISTS (SELECT 1 FROM tb_solicitud 
               WHERE tb_solicitud_id = p_idSolicitud 
                 AND tb_solicitud_fechoriniate IS NULL ) THEN
        UPDATE tb_solicitud 
        SET tb_solicitud_fechoriniate = NOW(), tb_solicitud_est = 'R'
        WHERE tb_solicitud_id = p_idSolicitud;
    END IF;
 
	
    INSERT INTO tb_trabajo (tb_solicitud_id, tb_usuario_id, tb_trabajo_des) 
    VALUES (p_idSolicitud, p_idUsuario, p_descripcion); 
	
    UPDATE tb_asignacion SET tb_asignacion_fechoriniate = NOW()
	WHERE tb_solicitud_id = p_idSolicitud AND tb_usuario_id = p_idUsuario;
	
    cResult := 'OK';
    RETURN cResult;

EXCEPTION
    WHEN OTHERS THEN
        RETURN 'Error: ' || SQLERRM;
END;
$$ LANGUAGE plpgsql;



CREATE OR REPLACE FUNCTION sp_terminar_trabajo(
    p_idTrabajo INT,  
    p_idUsuario INT,  
    p_idSolicitud INT
) 
RETURNS TEXT AS $$
DECLARE
    cResult TEXT;
BEGIN
			 
	UPDATE tb_trabajo SET tb_trabajo_fechorter = NOW()
	WHERE tb_trabajo_id = p_idTrabajo;
	
	UPDATE tb_asignacion SET tb_asignacion_fechorterate = NOW()
	WHERE tb_solicitud_id = p_idSolicitud AND tb_usuario_id = p_idUsuario;	 
	
    cResult := 'OK';
    RETURN cResult;

EXCEPTION
    WHEN OTHERS THEN
        RETURN 'Error: ' || SQLERRM;
END;
$$ LANGUAGE plpgsql;



CREATE OR REPLACE FUNCTION sp_finalizar_solicitud(
    p_idUsuario INT,  
    p_idSolicitud INT
) 
RETURNS TEXT AS $$
DECLARE
    cResult TEXT;
BEGIN
	IF EXISTS (SELECT 1 FROM tb_asignacion 
               WHERE tb_solicitud_id = p_idSolicitud 
                 AND tb_usuario_id = p_idUsuario AND tb_asignacion_coo = FALSE ) THEN
				 
        cResult := 'Lo sentimos! Solo el coordinador puede finalizar la solicitud.';
        RETURN cResult;
    END IF;
	
	UPDATE tb_solicitud SET tb_solicitud_fechorterate = NOW() , tb_solicitud_est = 'T'
	WHERE tb_solicitud_id = p_idSolicitud;
	
    cResult := 'OK';
    RETURN cResult;

EXCEPTION
    WHEN OTHERS THEN
        RETURN 'Error: ' || SQLERRM;
END;
$$ LANGUAGE plpgsql;


CREATE OR REPLACE FUNCTION sp_eliminar_usuario(
    p_idUsuario INT
) 
RETURNS TEXT AS $$

DECLARE
    cResult TEXT;
BEGIN

    DELETE FROM tb_usuario 
    WHERE tb_usuario_id = p_id_usuario;

    cResult := 'OK';
    RETURN cResult;

EXCEPTION
    WHEN OTHERS THEN
        RETURN 'Error: ' || SQLERRM;
END;

$$ LANGUAGE plpgsql;



CREATE OR REPLACE FUNCTION sp_editar_cliente(
    p_tb_usuario_id INT,          -- ID del usuario a editar
    p_tipdocide CHAR(1),          -- Tipo de documento de identidad
    p_numdocide VARCHAR(15),      -- Número de documento de identidad
    p_apepat VARCHAR,             -- Apellido paterno
    p_apemat VARCHAR,             -- Apellido materno
    p_nombre VARCHAR,             -- Nombre del cliente
    p_razon_social VARCHAR,       -- Razón social
    p_direccion VARCHAR,          -- Dirección
    p_telefono CHAR(9),           -- Teléfono
    p_corele VARCHAR,             -- Correo electrónico
    p_con CHAR(6),                -- Contraseña
    p_tb_rol_id INT               -- Rol del usuario
)
RETURNS VARCHAR AS $$
DECLARE
    cResult VARCHAR(100);
    v_tb_cliente_id INT;
BEGIN

    IF NOT EXISTS (SELECT 1 FROM tb_usuario WHERE tb_usuario_id = p_tb_usuario_id) THEN
        cResult := 'El usuario con ID ' || p_tb_usuario_id || ' no existe.';
        RETURN cResult;
    END IF;

    SELECT tb_cliente_id INTO v_tb_cliente_id
    FROM tb_usuario
    WHERE tb_usuario_id = p_tb_usuario_id;

    IF NOT EXISTS (SELECT 1 FROM tb_cliente WHERE tb_cliente_id = v_tb_cliente_id) THEN
        cResult := 'El cliente asociado al usuario con ID ' || p_tb_usuario_id || ' no existe.';
        RETURN cResult;
    END IF;

    IF EXISTS (
        SELECT 1
        FROM tb_usuario
        WHERE tb_usuario_corele = p_corele
          AND tb_usuario_id != p_tb_usuario_id
    ) THEN
        cResult := 'El correo ' || p_corele || ' ya está en uso por otro usuario.';
        RETURN cResult;
    END IF;

    UPDATE tb_cliente
    SET
        tb_cliente_tipdocide = p_tipdocide,
        tb_cliente_numdocide = p_numdocide,
        tb_cliente_razsoc = p_razon_social,
        tb_cliente_dir = p_direccion,
        tb_cliente_tel = p_telefono
    WHERE tb_cliente_id = v_tb_cliente_id;

    UPDATE tb_usuario
    SET
        tb_usuario_tipdocide = p_tipdocide,
        tb_usuario_numdocide = p_numdocide,
        tb_usuario_apepat = p_apepat,
        tb_usuario_apemat = p_apemat,
        tb_usuario_nom = p_nombre,
        tb_usuario_corele = p_corele,
        tb_usuario_con = p_con,
        tb_rol_id = p_tb_rol_id
    WHERE tb_usuario_id = p_tb_usuario_id;

    cResult := 'OK';
    RETURN cResult;

EXCEPTION
    WHEN OTHERS THEN
        RETURN 'Error: ' || SQLERRM;
END;
$$ LANGUAGE plpgsql;


CREATE OR REPLACE FUNCTION sp_eliminar_cliente(
    p_idUsuario INT
) 
RETURNS TEXT AS $$

DECLARE
    cResult TEXT;
	v_tb_cliente_id INT;
BEGIN
    SELECT tb_cliente_id INTO v_tb_cliente_id
    FROM tb_usuario
    WHERE tb_usuario_id = p_idUsuario;

    IF NOT EXISTS (SELECT 1 FROM tb_cliente WHERE tb_cliente_id = v_tb_cliente_id) THEN
        cResult := 'El cliente asociado al usuario con ID ' || p_idUsuario || ' no existe.';
        RETURN cResult;
    END IF;
	
    DELETE FROM tb_usuario 
    WHERE tb_usuario_id = p_idUsuario;
	
	DELETE FROM tb_cliente
    WHERE tb_cliente_id = v_tb_cliente_id;

    cResult := 'OK';
    RETURN cResult;

EXCEPTION
    WHEN OTHERS THEN
        RETURN 'Error: ' || SQLERRM;
END;

$$ LANGUAGE plpgsql;

INSERT INTO tb_rol(tb_rol_id,tb_rol_nom) VALUES(1,'Administrador');
INSERT INTO tb_rol(tb_rol_id,tb_rol_nom) VALUES(2,'Diseñador');
INSERT INTO tb_rol(tb_rol_id,tb_rol_nom) VALUES(3,'Analista');
INSERT INTO tb_rol(tb_rol_id,tb_rol_nom) VALUES(4,'Desarrollador');
INSERT INTO tb_rol(tb_rol_id,tb_rol_nom) VALUES(5,'Programador');
INSERT INTO tb_rol(tb_rol_id,tb_rol_nom) VALUES(6,'Cliente');


SELECT sp_insertar_usuario('1','12345678','Salazar', 'Perez', 'Alberto','admin@gmail.com','123456', TRUE, 'S',1);
SELECT sp_insertar_usuario('1', '12345678', 'Pérez', 'Gómez', 'Juan', 'juan.perez@gmail.com', '123456', TRUE, 'S', 1);
SELECT sp_insertar_usuario('1', '23456789', 'Martínez', 'López', 'Ana', 'ana.martinez@gmail.com', '234567', TRUE, 'E', 2);
SELECT sp_insertar_usuario('1', '34567890', 'Sánchez', 'Hernández', 'Luis', 'luis.sanchez@gmail.com', '345678', TRUE, 'E', 2);
SELECT sp_insertar_usuario('1', '45678901', 'González', 'Fernández', 'Marta', 'marta.gonzalez@gmail.com', '456789', TRUE, 'E', 3);
SELECT sp_insertar_usuario('1', '56789012', 'Rodríguez', 'Ramírez', 'Carlos', 'carlos.rodriguez@gmail.com', '567890', TRUE, 'E', 3);
SELECT sp_insertar_usuario('1', '67890123', 'Díaz', 'Moreno', 'Pedro', 'pedro.diaz@gmail.com', '678901', TRUE, 'E', 4);
SELECT sp_insertar_usuario('1', '78901234', 'Jiménez', 'Ruiz', 'Laura', 'laura.jimenez@gmail.com', '789012', TRUE, 'E', 4);
SELECT sp_insertar_usuario('1', '89012345', 'Vázquez', 'Torres', 'José', 'jose.vazquez@gmail.com', '890123', TRUE, 'E', 4);
SELECT sp_insertar_usuario('1', '90123456', 'García', 'Álvarez', 'María', 'maria.garcia@gmail.com', '901234', TRUE, 'E', 4);
SELECT sp_insertar_usuario('1', '11223344', 'Fernández', 'Castro', 'Antonio', 'antonio.fernandez@gmail.com', '112233', TRUE, 'E', 5);
SELECT sp_insertar_usuario('1', '22334455', 'López', 'Navarro', 'Sara', 'sara.lopez@gmail.com', '223344', TRUE, 'E', 5);
SELECT sp_insertar_usuario('1', '33445566', 'Morales', 'Soto', 'David', 'david.morales@gmail.com', '334455', TRUE, 'E', 4);
SELECT sp_insertar_usuario('1', '44556677', 'Ruiz', 'Paredes', 'Cristina', 'cristina.ruiz@gmail.com', '445566', TRUE, 'E', 2);
SELECT sp_insertar_usuario('1', '55667788', 'Torres', 'Méndez', 'Juan Carlos', 'juancarlos.torres@gmail.com', '556677', TRUE, 'E', 3);
SELECT sp_insertar_usuario('1', '66778899', 'Ramírez', 'Jiménez', 'Pedro', 'pedro.ramirez@gmail.com', '667788', TRUE, 'E', 3);
SELECT sp_insertar_usuario('1', '77889900', 'Soto', 'Morales', 'Claudia', 'claudia.soto@gmail.com', '778899', TRUE, 'E', 4);
SELECT sp_insertar_usuario('1', '88990011', 'Castro', 'González', 'Beatriz', 'beatriz.castro@gmail.com', '889900', TRUE, 'E', 5);
SELECT sp_insertar_usuario('1', '99001122', 'Paredes', 'López', 'Carlos', 'carlos.paredes@gmail.com', '990011', TRUE, 'E', 2);
SELECT sp_insertar_usuario('1', '10111213', 'Navarro', 'Vázquez', 'Francisco', 'francisco.navarro@gmail.com', '101112', TRUE, 'E', 3);
SELECT sp_insertar_usuario('1', '11223344', 'Álvarez', 'García', 'Paula', 'paula.alvarez@gmail.com', '112233', TRUE, 'E', 2);
SELECT sp_insertar_usuario('1', '22334455', 'Hernández', 'Morales', 'Alba', 'alba.hernandez@gmail.com', '223344', TRUE, 'S', 1);


select * from tb_cliente;
select * from tb_usuario;
select * from tb_rol;



-- Database: trab3inf1416

CREATE DATABASE trab3inf1416
  WITH OWNER = postgres
       ENCODING = 'UTF8'
       TABLESPACE = pg_default
       LC_COLLATE = 'pt_BR.UTF-8'
       LC_CTYPE = 'pt_BR.UTF-8'
       CONNECTION LIMIT = -1;

-- Schema: public

CREATE SCHEMA public
  AUTHORIZATION postgres;

GRANT ALL ON SCHEMA public TO postgres;
GRANT ALL ON SCHEMA public TO public;
COMMENT ON SCHEMA public
  IS 'standard public schema';



-- Table: grupos

-- DROP TABLE grupos;

CREATE TABLE grupos
(
  gid serial NOT NULL,
  grupo_name character varying(50),  
  created_at timestamp without time zone,
  CONSTRAINT grupo_pkey PRIMARY KEY (gid)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE grupos
  OWNER TO postgres;


-- Table: usuario

-- DROP TABLE usuarios;

CREATE TABLE usuarios
(
  id serial NOT NULL,
  user_name character varying(50),
  login_name character varying(20),
  user_group_fk integer,
  user_password character varying(32),
  user_url_pub character varying(255),
  user_tan_list integer,	
  disabled boolean,
  salt integer,
  created_at timestamp without time zone,
  blocked_at timestamp without time zone,
  CONSTRAINT usuario_pkey PRIMARY KEY (id),
  CONSTRAINT usuario_group_fkey FOREIGN KEY (user_group_fk)
      REFERENCES public.grupos (gid) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
  CONSTRAINT usuario_user_name_unique 
)
WITH (
  OIDS=FALSE
);
ALTER TABLE usuarios
  OWNER TO postgres;



-- Table: tan_lists

-- DROP TABLE tan_lists;

CREATE TABLE tan_lists
(
  id serial NOT NULL,
  tan_item character varying(32), 
  id_user_fk integer,  
  order_user integer,
  created_at timestamp without time zone,
  CONSTRAINT tan_lists_pkey PRIMARY KEY (id),
  CONSTRAINT usuario_tan_list_fkey FOREIGN KEY (id_user_fk)
      REFERENCES public.usuarios (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);
ALTER TABLE tan_lists
  OWNER TO postgres;

-- Table: mensagens

-- DROP TABLE mensagens;

CREATE TABLE mensagens
(
  id serial NOT NULL,
  msg_text character varying(150),  
  CONSTRAINT msg_pkey PRIMARY KEY (id)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE mensagens
  OWNER TO postgres;

-- Table: registros

-- DROP TABLE registros;

CREATE TABLE registros
(
  id serial NOT NULL,
  id_msg_fk integer,
  id_user_fk integer, 
  arq_name character varying(100), 
  created_at timestamp without time zone,
  CONSTRAINT registro_pkey PRIMARY KEY (id),
  CONSTRAINT usuario_registro_fkey FOREIGN KEY (id_user_fk)
      REFERENCES public.usuarios (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT mensagem_registro_fkey FOREIGN KEY (id_msg_fk)
      REFERENCES public.mensagens (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);
ALTER TABLE registros
  OWNER TO postgres;


-- Input de dados

INSERT INTO grupos (grupo_name, created_at)
values
('Administrador', now()),
('Usuario', now());

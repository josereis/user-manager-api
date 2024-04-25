CREATE SEQUENCE IF NOT EXISTS public.tb_roles_id_seq
    INCREMENT 1
    START 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    CACHE 1
;

CREATE TABLE IF NOT EXISTS public.tb_roles
(
    active boolean DEFAULT true,
    deleted boolean DEFAULT false,
    created_at timestamp(6) without time zone,
    id bigint NOT NULL DEFAULT nextval('tb_roles_id_seq'::regclass),
    updated_at timestamp(6) without time zone,
    version bigint,
    uuid uuid,
    description character varying(255) COLLATE pg_catalog."default",
    name character varying(255) COLLATE pg_catalog."default",
    pretty_name character varying(255) COLLATE pg_catalog."default",
    CONSTRAINT tb_roles_pkey PRIMARY KEY (id),
    CONSTRAINT tb_roles_uuid_key UNIQUE (uuid)
);

INSERT INTO public.tb_roles (active,deleted,created_at,updated_at,"version",uuid,description,name,pretty_name) VALUES
	 (true,false,current_timestamp,current_timestamp,0,'60febc0c-f421-4b52-b540-4f7f797ddbe1','Owner','OWNER','Owner'),
	 (true,false,current_timestamp,current_timestamp,0,'5dcb5cf5-578d-4452-8dac-92676420070f','Administrator','ADMIN','Admin');
;

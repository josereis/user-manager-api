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
)
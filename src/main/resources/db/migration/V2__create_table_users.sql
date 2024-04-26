CREATE SEQUENCE IF NOT EXISTS public.tb_users_id_seq
    INCREMENT 1
    START 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    CACHE 1;

CREATE TABLE IF NOT EXISTS public.tb_users
(
    id bigint NOT NULL DEFAULT nextval('tb_users_id_seq'::regclass),
    created_at timestamp(6) without time zone,
    deleted boolean DEFAULT false,
    updated_at timestamp(6) without time zone,
    uuid uuid,
    version bigint,
    email character varying(100) COLLATE pg_catalog."default" NOT NULL,
    name character varying(100) COLLATE pg_catalog."default" NOT NULL,
    password text COLLATE pg_catalog."default",
    situation character varying(50) COLLATE pg_catalog."default" DEFAULT 'PENDING_ACTIVATION'::character varying,
    username character varying(100) COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT tb_users_pkey PRIMARY KEY (id),
    CONSTRAINT uk_8n82lwp7lflhwda2v2v3wckc9 UNIQUE (username),
    CONSTRAINT uk_eyfurj75u6et4opdr6ex74e0v UNIQUE (uuid),
    CONSTRAINT uk_grd22228p1miaivbn9yg178pm UNIQUE (email),
    CONSTRAINT tb_users_situation_check CHECK (situation::text = ANY (ARRAY['PENDING_ACTIVATION'::character varying, 'ACTIVE'::character varying, 'CANCELED'::character varying, 'SUSPENDED'::character varying]::text[]))
);

CREATE SEQUENCE IF NOT EXISTS public.tb_users_roles_id_seq
    INCREMENT 1
    START 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    CACHE 1;

CREATE TABLE IF NOT EXISTS public.tb_users_roles
(
    id bigint NOT NULL DEFAULT nextval('tb_users_roles_id_seq'::regclass),
    created_at timestamp(6) without time zone,
    deleted boolean DEFAULT false,
    updated_at timestamp(6) without time zone,
    uuid uuid,
    version bigint,
    role_id bigint,
    user_id bigint,
    CONSTRAINT tb_users_roles_pkey PRIMARY KEY (id),
    CONSTRAINT uk_8uankw62lmsimqywskpjy3lnr UNIQUE (uuid),
    CONSTRAINT fk5xc4yvfrjcy8bl01kq3crp8pg FOREIGN KEY (user_id)
        REFERENCES public.tb_users (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT fkj5qged12p22eloqw5g4f9hm2e FOREIGN KEY (role_id)
        REFERENCES public.tb_roles (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
);

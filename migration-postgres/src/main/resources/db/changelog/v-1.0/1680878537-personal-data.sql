--liquibase formatted sql

--changeset demidrolll:1680878537
ALTER TABLE IF EXISTS public.client
    ADD CONSTRAINT client_personal_data_unique UNIQUE (personal_data_id)
    INCLUDE (personal_data_id);

CREATE TABLE IF NOT EXISTS public.personal_data
(
    id bigserial NOT NULL,
    first_name character varying(100) NOT NULL,
    last_name character varying(100),
    middle_name character varying(100),
    sex character varying(10) NOT NULL,
    birth_date date NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT client_personal_data_id FOREIGN KEY (id)
        REFERENCES public.client (personal_data_id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
        NOT VALID
);

ALTER TABLE IF EXISTS public.personal_data
    OWNER to admin;

--liquibase formatted sql

--changeset demidrolll:1680874863
CREATE TABLE public.client
(
    id bigserial NOT NULL,
    personal_data_id bigint,
    PRIMARY KEY (id)
);

ALTER TABLE IF EXISTS public.client
    OWNER to admin;

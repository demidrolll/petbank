--liquibase formatted sql

--changeset demidrolll:1681119487037
ALTER TABLE public.personal_data DROP CONSTRAINT client_personal_data_id;

ALTER TABLE IF EXISTS public.client
    ADD CONSTRAINT client_personal_data_id FOREIGN KEY (personal_data_id)
    REFERENCES public.personal_data (id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION
    NOT VALID;

CREATE INDEX IF NOT EXISTS fki_client_personal_data_id
    ON public.client(personal_data_id);

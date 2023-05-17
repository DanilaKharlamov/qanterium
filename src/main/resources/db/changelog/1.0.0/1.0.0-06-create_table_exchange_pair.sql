--liquibase formatted sql
--changeset dkharlamov:1.0.0-06_create_table_exchange_pair labels:qanterium
CREATE TABLE exchange_pair
(
    id                  BIGINT GENERATED ALWAYS AS IDENTITY
        CONSTRAINT exchange_pair_pk PRIMARY KEY,
    base_currency_id    INTEGER            NOT NULL,
    related_currency_id INTEGER            NOT NULL,
    value               NUMERIC(100, 4)    NOT NULL,
    created_at          DATE DEFAULT now() NOT NULL,
    created_by_user  BOOLEAN,

    CONSTRAINT base_currency_fk FOREIGN KEY (base_currency_id) REFERENCES transaction_currency (id),
    CONSTRAINT related_currency_fk FOREIGN KEY (related_currency_id) REFERENCES transaction_currency (id)
);
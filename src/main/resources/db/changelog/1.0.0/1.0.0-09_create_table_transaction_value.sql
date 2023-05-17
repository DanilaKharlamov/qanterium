--liquibase formatted sql
--changeset dkharlamov:1.0.0-09_create_table_transaction_value labels:qanterion
CREATE TABLE transaction_value
(
    id                      BIGINT GENERATED ALWAYS AS IDENTITY,
    transaction_id          BIGINT                                 NOT NULL,
    transaction_currency_id INTEGER,
    exchange_pair_id        BIGINT,
    value                   NUMERIC(100, 4),
    is_base                 BOOLEAN,
    created_at              TIMESTAMP WITH TIME ZONE DEFAULT now() NOT NULL,
    updated_at              TIMESTAMP WITH TIME ZONE DEFAULT now() NOT NULL,

    CONSTRAINT transaction_fk FOREIGN KEY (transaction_id) REFERENCES transaction (id),
    CONSTRAINT transaction_currency_fk FOREIGN KEY (transaction_currency_id) REFERENCES transaction_currency (id),
    CONSTRAINT exchange_pair_id FOREIGN KEY (exchange_pair_id) REFERENCES exchange_pair (id),
    CONSTRAINT transaction_value_pk PRIMARY KEY (transaction_id, transaction_currency_id)
);
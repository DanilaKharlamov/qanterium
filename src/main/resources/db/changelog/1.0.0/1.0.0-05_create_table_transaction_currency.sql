--liquibase formatted sql
--changeset dkharlamov:1.0.0-05_create_table_transaction_currency labels:qanterion
CREATE TABLE transaction_currency
(
    id        INTEGER GENERATED BY DEFAULT AS IDENTITY
        CONSTRAINT transaction_currency_pk PRIMARY KEY,
    code      VARCHAR(5)  NOT NULL,
    full_name VARCHAR(30) NOT NULL
);
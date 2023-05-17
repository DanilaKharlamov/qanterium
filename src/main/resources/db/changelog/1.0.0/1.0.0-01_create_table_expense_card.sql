--liquibase formatted sql
--changeset dkharlamov:1.0.0-01_create_table_expense_card labels:qanterion
CREATE TABLE expense_card
(
    id          BIGINT GENERATED ALWAYS AS IDENTITY
        CONSTRAINT expense_card_pk PRIMARY KEY,
    name        VARCHAR(50)                            NOT NULL,
    description VARCHAR(255),
    created_at  DATE                     DEFAULT now() NOT NULL,
    updated_at  TIMESTAMP WITH TIME ZONE DEFAULT now() NOT NULL
);
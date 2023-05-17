--liquibase formatted sql
--changeset dkharlamov:1.0.0-04_create_table_transaction labels:qanterion
CREATE TABLE transaction
(
    id          BIGINT GENERATED ALWAYS AS IDENTITY
        CONSTRAINT transaction_pk PRIMARY KEY,
    name        VARCHAR(50)                            NOT NULL,
    description VARCHAR(255),
    owner_id    BIGINT                                 NOT NULL,
    created_at  DATE                     DEFAULT now() NOT NULL,
    updated_at  TIMESTAMP WITH TIME ZONE DEFAULT now() NOT NULL,

    CONSTRAINT member_owner_fk FOREIGN KEY (owner_id) REFERENCES member (id)
);
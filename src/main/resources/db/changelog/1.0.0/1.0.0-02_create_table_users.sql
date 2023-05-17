--liquibase formatted sql
--changeset dkharlamov:1.0.0-02_create_table_users labels:qanterium
CREATE TABLE users
(
    id        BIGINT GENERATED ALWAYS AS IDENTITY
        CONSTRAINT user_pk PRIMARY KEY,
    username  VARCHAR(25)  NOT NULL UNIQUE,
    email     VARCHAR(100) NOT NULL UNIQUE,
    password  VARCHAR(255) NOT NULL,
    firstname VARCHAR(25),
    lastname  VARCHAR(40),
    role      VARCHAR(10)
)
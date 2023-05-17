--liquibase formatted sql
--changeset dkharlamov:1.0.0-03-create_table_member labels:qanterion
CREATE TABLE member
(
    id              BIGINT GENERATED ALWAYS AS IDENTITY
        CONSTRAINT member_pk PRIMARY KEY,
    name            VARCHAR(50) NOT NULL,
    role            VARCHAR(10) NOT NULL,
    expense_card_id BIGINT      NOT NULL,
    user_id         BIGINT,

    CONSTRAINT user_fk FOREIGN KEY (user_id) REFERENCES users (id),
    CONSTRAINT expense_card_fk FOREIGN KEY (expense_card_id) REFERENCES expense_card (id)
);
--liquibase formatted sql
--changeset dkharlamov:1.0.0-07_create_table_xref_transaction_to_member_debts labels:qanterion
CREATE TABLE xref_transaction_to_member_debts
(
    transaction_id BIGINT NOT NULL,
    member_id      BIGINT NOT NULL,

    CONSTRAINT xref_transaction_member_debts_transaction_id_fk FOREIGN KEY (transaction_id) REFERENCES transaction (id),
    CONSTRAINT xref_transaction_member_debts_member_id_fk FOREIGN KEY (member_id) REFERENCES member (id),
    CONSTRAINT xref_transaction_member_debts_pk PRIMARY KEY (member_id, transaction_id)
);
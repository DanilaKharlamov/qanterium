--liquibase formatted sql
--changeset dkharlamov:1.0.0-08_add_records_to_transaction_currency lables:qanterion
INSERT INTO transaction_currency (id, code, full_name)
VALUES (1, 'USD', 'US Dollar'),
       (2, 'EUR', 'Euro'),
       (3, 'RUB', 'Russian Rouble'),
       (4, 'GEL', 'Georgian Lari');
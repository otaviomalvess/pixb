INSERT INTO key_type(id, type)
VALUES
        (0, 'CPF'),
        (1, 'E-mail'),
        (2, 'Telefone'),
        (3, 'Chave Aleatória');

INSERT INTO account(account, balance, bank, branch, cd_cpf, name)
VALUES
        (0, 100.0, 000, 00, '11111111101', 'Ana'),
        (0, 100.0, 000, 00, '22222222201', 'Bob');

INSERT INTO directory(account, bank, branch, creation_date, key_type, cd_cpf, key, owner)
VALUES 
        (0, 00, 000, NOW(), 1, '11111111101', 'mail@mail.com', 'Ana'),
        (0, 00, 000, NOW(), 2, '22222222201', '+5561123456789', 'Bob'),
        (0, 00, 000, NOW(), 0, '22222222201', '22222222201', 'Bob');

-- INSERT INTO pix_request(cd_cpf, value, resolved)
-- VALUES 
--         ('11111111101', 20.0, 0),
--         ('22222222201', 20.0, 1),
--         ('33333333301', 20.0, 0),
--         ('44444444401', 20.0, 2),
--         ('55555555501', 20.0, 2);
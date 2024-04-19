INSERT INTO key_type(id, type)
VALUES
        (0, 'CPF'),
        (1, 'E-mail'),
        (2, 'Telefone'),
        (3, 'Chave Aleatória');

INSERT INTO directory(pkey, pkey_type, cpf, owner, creation_date)
VALUES 
        ('mail@mail.com', 1, '11111111101', 'Ana', NOW()),
        ('+5561123456789', 2, '22222222201', 'Bob', NOW()),
        ('11111111101', 0, '11111111101', 'Ana', NOW());

INSERT INTO account(cpf, name, bank, branch, account, balance)
VALUES
        ('11111111101', 'Ana', '', '', '', 100.0),
        ('22222222201', 'Bob', '', '', '', 100.0);

-- INSERT INTO pix_request(cpf, value, resolved)
-- VALUES 
--         ('11111111101', 20.0, 0),
--         ('22222222201', 20.0, 1),
--         ('33333333301', 20.0, 0),
--         ('44444444401', 20.0, 2),
--         ('55555555501', 20.0, 2);
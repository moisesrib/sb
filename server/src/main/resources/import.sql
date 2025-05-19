-- Criação de roles padrão do sistema
INSERT INTO roles (id, name, description)
SELECT gen_random_uuid(), 'ADMIN', 'Acesso total ao sistema'
WHERE NOT EXISTS (SELECT 1 FROM roles WHERE name = 'ADMIN');

INSERT INTO roles (id, name, description)
SELECT gen_random_uuid(), 'MANAGER', 'Acesso de gerenciamento ao sistema'
WHERE NOT EXISTS (SELECT 1 FROM roles WHERE name = 'MANAGER');

INSERT INTO roles (id, name, description)
SELECT gen_random_uuid(), 'SELLER', 'Acesso básico ao sistema'
WHERE NOT EXISTS (SELECT 1 FROM roles WHERE name = 'SELLER');

INSERT INTO roles (id, name, description)
SELECT gen_random_uuid(), 'OWNER', 'Acesso limitado somente leitura'
WHERE NOT EXISTS (SELECT 1 FROM roles WHERE name = 'OWNER');

INSERT INTO roles (id, name, description)
SELECT gen_random_uuid(), 'USER', 'Acesso limitado somente leitura'
WHERE NOT EXISTS (SELECT 1 FROM roles WHERE name = 'USER');


-- Criar usuário admin com role ADMIN diretamente
INSERT INTO users (id, name, email, password, active, role_id)
SELECT 
    gen_random_uuid(), 
    'Administrador', 
    'admin@semprebella.com',
    '$2a$10$c68am.JZ3nfWEyPSvHEi5.JjcA1iQStDLb/mBEafB2n.B9x6DjFwi', -- senha: admin123
    true,
    (SELECT id FROM roles WHERE name = 'ADMIN')
WHERE NOT EXISTS (SELECT 1 FROM users WHERE email = 'admin@semprebella.com');

-- Atualizar usuários existentes que não têm role definida
UPDATE users
SET role_id = (SELECT id FROM roles WHERE name = 'USER')
WHERE role_id IS NULL; 
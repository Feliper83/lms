-- ========================
-- AUTORES
-- ========================
INSERT INTO AUTHORS (id, name) VALUES (1, 'Gabriel García Márquez');
INSERT INTO AUTHORS (id, name) VALUES (2, 'Isabel Allende');
INSERT INTO AUTHORS (id, name) VALUES (3, 'J.K. Rowling');

-- ========================
-- LIBROS (tienen que usar IDs de autores existentes)
-- ========================
INSERT INTO roles (id, name) VALUES
(1, 'ROLE_ADMIN'),
(2, 'ROLE_USER');

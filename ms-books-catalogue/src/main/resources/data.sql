-- Libro 1: Con stock y visible (ÉXITO)
INSERT INTO books (id, title, author, publication_date, category, isbn, rating, visible, stock)
VALUES (1, 'Cien años de soledad', 'Gabriel García Márquez', DATE '1967-05-30', 'Ficción', '9788437604947', 5, true, 10);

-- Libro 2: Sin stock (FALLO DE VALIDACIÓN)
INSERT INTO books (id, title, author, publication_date, category, isbn, rating, visible, stock)
VALUES (2, 'Don Quijote de la Mancha', 'Miguel de Cervantes', DATE '1605-01-16', 'Ficción', '9788491050293', 5, true, 0);

-- Libro 7: Invisible (FALLO DE VALIDACIÓN)
INSERT INTO books (id, title, author, publication_date, category, isbn, rating, visible, stock)
VALUES (7, 'La casa de los espíritus', 'Isabel Allende', DATE '1982-10-01', 'Ficción', '9788408052202', 4, false, 5);
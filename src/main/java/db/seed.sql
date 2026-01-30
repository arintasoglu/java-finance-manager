USE finance;

INSERT INTO accountf (id, username, email, passwordf, role)
VALUES (
    '1',
    'admin',
    'admin@finance.local',
    '$2a$12$f9HrNBJ1C0m02JB.0aihbeULrNIIItg5m0dv8KihghsUin3TlR3IO',
    'ADMIN'
);


INSERT INTO accountf (id, username, email, passwordf, role)
VALUES (
    '2',
    'user',
    'admin',
    '$2a$12$f9HrNBJ1C0m02JB.0aihbeULrNIIItg5m0dv8KihghsUin3TlR3IO',
    'USER'
);



INSERT INTO categories (account_id, name)
VALUES
('2', 'Food'),
('2', 'Rent'),
('2', 'Salary');


INSERT INTO transactions (account_id, type, amount, datef, descriptionf, categoryName)
VALUES
('2', 'EXPENSE', 12.50, '2026-01-10', 'Kaffee', 'Lebensmittel'),
('2', 'EXPENSE', 800.00, '2026-01-01', 'Miete Januar', 'Miete'),
('2', 'INCOME', 2500.00, '2026-01-05', 'Monatsgehalt', 'Gehalt');

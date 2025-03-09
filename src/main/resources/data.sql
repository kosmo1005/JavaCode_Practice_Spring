INSERT INTO my_user (id, name, email)
VALUES ('550e8400-e29b-41d4-a716-446655440000', 'John Doe', 'john@example.com'),
       ('550e8400-e29b-41d4-a716-446655440001', 'Jane Doe', 'jane@example.com');

INSERT INTO user_order (user_id, status, total_price)
VALUES ('550e8400-e29b-41d4-a716-446655440000', 'NEW', 1125.00),
       ('550e8400-e29b-41d4-a716-446655440001', 'NEW', 2250.00),
       ('550e8400-e29b-41d4-a716-446655440000', 'NEW', 3375.00),
       ('550e8400-e29b-41d4-a716-446655440001', 'NEW', 4500.00);

INSERT INTO good (name, price, order_id)
VALUES ('Laptop', 1000.00, 1),
       ('Mouse', 50.00, 1),
       ('Keyboard', 75.00, 1),
       ('Laptop', 2000.00, 2),
       ('Mouse', 100.00, 2),
       ('Keyboard', 150.00, 2),
       ('Laptop', 3000.00, 3),
       ('Mouse', 150.00, 3),
       ('Keyboard', 225.00, 3),
       ('Laptop', 4000.00, 4),
       ('Mouse', 200.00, 4),
       ('Keyboard', 300.00, 4);
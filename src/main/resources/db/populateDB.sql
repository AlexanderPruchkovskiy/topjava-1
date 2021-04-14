DELETE FROM user_roles;
DELETE FROM meals;
DELETE FROM users;
ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO users (name, email, password) VALUES
  ('User', 'user@yandex.ru', 'password'),
  ('Admin', 'admin@gmail.com', 'admin');

INSERT INTO user_roles (role, user_id) VALUES
  ('ROLE_USER', 100000),
  ('ROLE_ADMIN', 100001);
INSERT INTO meals ( user_id, description, dateTime, calories) VALUES
(100000, 'Завтрак', '2020-02-15 10:10:25',300 ),
(100000, 'Обед', '2020-02-15 14:10:00',330 ),
( 100000, 'Ужин', '2020-02-15 18:10:10',290 ),
( 100001, 'Завтрак', '2020-02-15 10:10:25',400 ),
( 100001, 'Обед', '2020-02-15 14:10:00',390 ),
( 100001, 'Ужин', '2020-02-15 18:10:10',295 );

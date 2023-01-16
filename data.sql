INSERT INTO final.exhibition_status (id, name) VALUES (1, 'ACTIVE');
INSERT INTO final.exhibition_status (id, name) VALUES (2, 'CANCELED');
INSERT INTO final.exhibition_status (id, name) VALUES (3, 'FINISHED');

INSERT INTO final.themes (id, name) VALUES (1, 'War');
INSERT INTO final.themes (id, name) VALUES (2, 'Nature');
INSERT INTO final.themes (id, name) VALUES (3, 'Art');
INSERT INTO final.themes (id, name) VALUES (4, 'Freedom');
INSERT INTO final.themes (id, name) VALUES (6, 'Life');
INSERT INTO final.themes (id, name) VALUES (8, 'Environment');

INSERT INTO final.halls (id, name, address, capacity)
VALUES (1, 'INTERNATIONAL EXHIBITION CENTRE', '15 Brovarskyi Ave., UA-02002 Kyiv, Ukraine', 100);
INSERT INTO final.halls (id, name, address, capacity)
VALUES (2, 'KYIV EXPO PLAZA', '1, Amsterdamska, str, Berezivka village , Ukraine', 250);
INSERT INTO final.halls (id, name, address, capacity)
VALUES (3, 'Radmir Expohall', 'Kharkiv, Pavlova Street, 271, Moscovskyi district', 5);
INSERT INTO final.halls (id, name, address, capacity)
VALUES (4, 'New hall', 'Sumy, Lushpy ave, 15', 25);
INSERT INTO final.halls (id, name, address, capacity)
VALUES (5, 'Нова зала проведення виставок', 'м. Суми вул. Інтернаціоналістів, 15', 100);

INSERT INTO final.exhibitions (id, title, description, theme_id, start_date, finish_date, open_time, close_time, price,
                               image, state)
VALUES (1, 'test exhibition', 'Very long descriptionddddddddddddddddddddddddddddddddddd', 6, '2022-09-12', '2022-09-30',
        '09:00:00', '18:00:00', 100, '6010.webp', 'CANCELED');
INSERT INTO final.exhibitions (id, title, description, theme_id, start_date, finish_date, open_time, close_time, price,
                               image, state)
VALUES (2, 'New art', 'Short description', 3, '2022-10-01', '2022-10-14', '08:00:00', '18:00:00', 151, 'image (1).jpg',
        'FINISHED');
INSERT INTO final.exhibitions (id, title, description, theme_id, start_date, finish_date, open_time, close_time, price,
                               image, state)
VALUES (15, 'Again', 'Exhibition for test', 2, '2022-11-01', '2023-12-01', '09:00:00', '18:00:00', 1250, 'image.jpg',
        'ACTIVE');
INSERT INTO final.exhibitions (id, title, description, theme_id, start_date, finish_date, open_time, close_time, price,
                               image, state)
VALUES (22, 'jjjjjjjjjj', 'jjjjjjjjjjjjjj', 1, '2022-10-19', '2022-10-20', '03:29:00', '04:29:00', 4444, 'images.jpg',
        'FINISHED');
INSERT INTO final.exhibitions (id, title, description, theme_id, start_date, finish_date, open_time, close_time, price,
                               image, state)
VALUES (23, 'Thuesday', 'again exhibition', 3, '2022-10-18', '2023-10-20', '05:35:00', '03:35:00', 111,
        'ocec-plan_exhibitions.jpg', 'ACTIVE');
INSERT INTO final.exhibitions (id, title, description, theme_id, start_date, finish_date, open_time, close_time, price,
                               image, state)
VALUES (24, 'Monday exhibition', 'new', 1, '2022-10-11', '2022-10-18', '19:36:00', '21:36:00', 250,
        'Designing-an-Exhibition-1.webp', 'FINISHED');
INSERT INTO final.exhibitions (id, title, description, theme_id, start_date, finish_date, open_time, close_time, price,
                               image, state)
VALUES (25, 'Seventh exhibition', 'only for test', 2, '2022-10-31', '2022-11-06', '09:00:00', '18:00:00', 500,
        'Designing-an-Exhibition-1.webp', 'FINISHED');
INSERT INTO final.exhibitions (id, title, description, theme_id, start_date, finish_date, open_time, close_time, price,
                               image, state)
VALUES (26, 'DTO', 'after dto change', 6, '2022-10-27', '2023-10-30', '10:00:00', '18:00:00', 123,
        'Yayoi-Kusama-The-Obliteration-Room-2002.jpg', 'ACTIVE');
INSERT INTO final.exhibitions (id, title, description, theme_id, start_date, finish_date, open_time, close_time, price,
                               image, state)
VALUES (27, 'Нова виставка',
        'Спроба створення нової виставки після локалізації для перевірки збереження тексту в базі даних.',
        4, '2022-11-02', '2023-11-30', '09:00:00', '18:00:00', 250, 'img_1_big.jpg', 'ACTIVE');
INSERT INTO final.exhibitions (id, title, description, theme_id, start_date, finish_date, open_time, close_time, price,
                               image, state)
VALUES (28, 'Hi-Tech AGRO', '"Hi-Tech AGRO" exposition gathers representatives of the newest technologies, tools and equipment used in
agribusiness for precision arable farming, automation and control, enterprise management.
"Hi-Tech AGRO" Exhibition dedicated to high technologies in agriculture.
The international agricultural exhibition "AGRO-2022" has already become a good tradition for Ukrainian farmers. It combines many expositions,
but the HiTech AGRO exhibition dedicated to high technologies in agriculture is especially attractive.
The Hi-Tech AGRO exposition attracts companies representing the latest technologies in the agricultural sector. They demonstrate cutting-edge
devices and equipment for the agro-industrial complex, agriculture, production processes, solutions for automation, accounting and control,
as well as for professional enterprise management. It is an exhibition for agriculture - a step towards the meeting of time.',
        8, '2022-11-05', '2023-11-06', '09:00:00', '18:00:00', 250, '151079271.jpg', 'ACTIVE');
INSERT INTO final.exhibitions (id, title, description, theme_id, start_date, finish_date, open_time, close_time, price,
                               image, state)
VALUES (29, 'нова виставка', 'Перша виставка після правки', 3, '2022-11-12', '2023-11-13', '09:00:00', '18:00:00', 125,
        'LOGO1.bmp', 'ACTIVE');
INSERT INTO final.exhibitions (id, title, description, theme_id, start_date, finish_date, open_time, close_time, price,
                               image, state)
VALUES (30, 'Тестування зміни стану',
        'Для успішного тестування стан виставки автоматично міняєтьсяна Завершено при даті, що менша за теперішню.',
        8, '2023-01-16', '2023-01-20', '09:00:00', '18:00:00', 250, 'гн.jpg', 'ACTIVE');

INSERT INTO final.exhibitions_halls (exhibition_id, hall_id) VALUES (1, 1);
INSERT INTO final.exhibitions_halls (exhibition_id, hall_id) VALUES (1, 3);
INSERT INTO final.exhibitions_halls (exhibition_id, hall_id) VALUES (1, 4);
INSERT INTO final.exhibitions_halls (exhibition_id, hall_id) VALUES (2, 1);
INSERT INTO final.exhibitions_halls (exhibition_id, hall_id) VALUES (2, 2);
INSERT INTO final.exhibitions_halls (exhibition_id, hall_id) VALUES (2, 3);
INSERT INTO final.exhibitions_halls (exhibition_id, hall_id) VALUES (23, 1);
INSERT INTO final.exhibitions_halls (exhibition_id, hall_id) VALUES (23, 2);
INSERT INTO final.exhibitions_halls (exhibition_id, hall_id) VALUES (28, 1);
INSERT INTO final.exhibitions_halls (exhibition_id, hall_id) VALUES (28, 2);
INSERT INTO final.exhibitions_halls (exhibition_id, hall_id) VALUES (28, 5);
INSERT INTO final.exhibitions_halls (exhibition_id, hall_id) VALUES (27, 3);
INSERT INTO final.exhibitions_halls (exhibition_id, hall_id) VALUES (27, 4);
INSERT INTO final.exhibitions_halls (exhibition_id, hall_id) VALUES (26, 4);
INSERT INTO final.exhibitions_halls (exhibition_id, hall_id) VALUES (29, 1);
INSERT INTO final.exhibitions_halls (exhibition_id, hall_id) VALUES (29, 2);
INSERT INTO final.exhibitions_halls (exhibition_id, hall_id) VALUES (29, 3);
INSERT INTO final.exhibitions_halls (exhibition_id, hall_id) VALUES (29, 4);
INSERT INTO final.exhibitions_halls (exhibition_id, hall_id) VALUES (29, 5);
INSERT INTO final.exhibitions_halls (exhibition_id, hall_id) VALUES (24, 3);
INSERT INTO final.exhibitions_halls (exhibition_id, hall_id) VALUES (25, 1);
INSERT INTO final.exhibitions_halls (exhibition_id, hall_id) VALUES (25, 4);
INSERT INTO final.exhibitions_halls (exhibition_id, hall_id) VALUES (15, 1);
INSERT INTO final.exhibitions_halls (exhibition_id, hall_id) VALUES (22, 1);
INSERT INTO final.exhibitions_halls (exhibition_id, hall_id) VALUES (22, 2);
INSERT INTO final.exhibitions_halls (exhibition_id, hall_id) VALUES (22, 3);
INSERT INTO final.exhibitions_halls (exhibition_id, hall_id) VALUES (30, 2);
INSERT INTO final.exhibitions_halls (exhibition_id, hall_id) VALUES (30, 5);

INSERT INTO final.tickets (id, user_id, exhibition_id, hall_id, price, state, operation_date)
VALUES (36, 26, 24, 3, 250, 'REFUNDED', '2022-10-27 00:20:10');
INSERT INTO final.tickets (id, user_id, exhibition_id, hall_id, price, state, operation_date)
VALUES (39, 26, 1, 3, 100, 'REFUNDED', '2022-10-27 00:21:01');
INSERT INTO final.tickets (id, user_id, exhibition_id, hall_id, price, state, operation_date)
VALUES (40, 26, 23, 1, 111, 'REFUNDED', '2022-10-27 00:30:46');
INSERT INTO final.tickets (id, user_id, exhibition_id, hall_id, price, state, operation_date)
VALUES (41, 26, 1, 4, 100, 'REFUNDED', '2022-10-27 00:31:00');
INSERT INTO final.tickets (id, user_id, exhibition_id, hall_id, price, state, operation_date)
VALUES (42, 26, 1, 4, 100, 'ACTIVE', '2022-10-27 00:31:02');
INSERT INTO final.tickets (id, user_id, exhibition_id, hall_id, price, state, operation_date)
VALUES (43, 26, 26, 4, 123, 'REFUNDED', '2022-10-27 23:44:13');
INSERT INTO final.tickets (id, user_id, exhibition_id, hall_id, price, state, operation_date)
VALUES (44, 26, 1, 1, 100, 'ACTIVE', '2022-11-01 15:51:47');
INSERT INTO final.tickets (id, user_id, exhibition_id, hall_id, price, state, operation_date)
VALUES (45, 26, 2, 1, 151, 'ACTIVE', '2022-11-01 23:17:14');
INSERT INTO final.tickets (id, user_id, exhibition_id, hall_id, price, state, operation_date)
VALUES (46, 26, 2, 3, 151, 'ACTIVE', '2022-11-01 23:17:23');
INSERT INTO final.tickets (id, user_id, exhibition_id, hall_id, price, state, operation_date)
VALUES (47, 26, 22, 3, 4444, 'ACTIVE', '2022-11-01 23:18:05');
INSERT INTO final.tickets (id, user_id, exhibition_id, hall_id, price, state, operation_date)
VALUES (48, 26, 22, 1, 4444, 'REFUNDED', '2022-11-01 23:38:34');
INSERT INTO final.tickets (id, user_id, exhibition_id, hall_id, price, state, operation_date)
VALUES (49, 26, 23, 2, 111, 'ACTIVE', '2022-11-01 23:38:55');
INSERT INTO final.tickets (id, user_id, exhibition_id, hall_id, price, state, operation_date)
VALUES (51, 26, 15, 1, 1250, 'ACTIVE', '2022-11-03 20:10:15');
INSERT INTO final.tickets (id, user_id, exhibition_id, hall_id, price, state, operation_date)
VALUES (52, 26, 1, 3, 100, 'ACTIVE', '2022-11-04 00:28:01');
INSERT INTO final.tickets (id, user_id, exhibition_id, hall_id, price, state, operation_date)
VALUES (60, 26, 1, 3, 100, 'ACTIVE', '2022-11-04 00:36:01');
INSERT INTO final.tickets (id, user_id, exhibition_id, hall_id, price, state, operation_date)
VALUES (61, 26, 1, 3, 100, 'REFUNDED', '2022-11-04 00:36:02');
INSERT INTO final.tickets (id, user_id, exhibition_id, hall_id, price, state, operation_date)
VALUES (62, 26, 1, 3, 100, 'REFUNDED', '2022-11-04 00:36:04');
INSERT INTO final.tickets (id, user_id, exhibition_id, hall_id, price, state, operation_date)
VALUES (63, 26, 1, 3, 100, 'REFUNDED', '2022-11-04 00:39:48');
INSERT INTO final.tickets (id, user_id, exhibition_id, hall_id, price, state, operation_date)
VALUES (64, 26, 1, 4, 100, 'REFUNDED', '2022-11-04 11:44:18');
INSERT INTO final.tickets (id, user_id, exhibition_id, hall_id, price, state, operation_date)
VALUES (65, 30, 15, 1, 1250, 'REFUNDED', '2022-11-04 15:16:10');
INSERT INTO final.tickets (id, user_id, exhibition_id, hall_id, price, state, operation_date)
VALUES (66, 30, 23, 2, 111, 'ACTIVE', '2022-11-04 15:16:36');
INSERT INTO final.tickets (id, user_id, exhibition_id, hall_id, price, state, operation_date)
VALUES (67, 26, 1, 1, 100, 'ACTIVE', '2022-11-04 18:26:03');
INSERT INTO final.tickets (id, user_id, exhibition_id, hall_id, price, state, operation_date)
VALUES (68, 26, 28, 1, 250, 'ACTIVE', '2022-11-04 19:00:44');
INSERT INTO final.tickets (id, user_id, exhibition_id, hall_id, price, state, operation_date)
VALUES (69, 26, 28, 5, 250, 'ACTIVE', '2022-11-04 19:01:03');
INSERT INTO final.tickets (id, user_id, exhibition_id, hall_id, price, state, operation_date)
VALUES (70, 26, 28, 1, 250, 'REFUNDED', '2022-11-04 19:31:45');
INSERT INTO final.tickets (id, user_id, exhibition_id, hall_id, price, state, operation_date)
VALUES (71, 26, 29, 1, 125, 'REFUNDED', '2022-11-12 23:32:39');
INSERT INTO final.tickets (id, user_id, exhibition_id, hall_id, price, state, operation_date)
VALUES (72, 26, 29, 2, 125, 'ACTIVE', '2022-11-12 23:35:09');
INSERT INTO final.tickets (id, user_id, exhibition_id, hall_id, price, state, operation_date)
VALUES (73, 26, 29, 2, 125, 'REFUNDED', '2022-11-12 23:35:12');
INSERT INTO final.tickets (id, user_id, exhibition_id, hall_id, price, state, operation_date)
VALUES (74, 31, 1, 4, 100, 'ACTIVE', '2022-11-13 21:40:02');
INSERT INTO final.tickets (id, user_id, exhibition_id, hall_id, price, state, operation_date)
VALUES (75, 31, 29, 3, 125, 'ACTIVE', '2022-11-13 21:40:20');
INSERT INTO final.tickets (id, user_id, exhibition_id, hall_id, price, state, operation_date)
VALUES (76, 31, 29, 3, 125, 'ACTIVE', '2022-11-13 21:40:21');
INSERT INTO final.tickets (id, user_id, exhibition_id, hall_id, price, state, operation_date)
VALUES (77, 31, 1, 4, 100, 'REFUNDED', '2022-11-13 21:40:54');
INSERT INTO final.tickets (id, user_id, exhibition_id, hall_id, price, state, operation_date)
VALUES (78, 26, 27, 4, 250, 'ACTIVE', '2022-12-12 17:48:33');
INSERT INTO final.tickets (id, user_id, exhibition_id, hall_id, price, state, operation_date)
VALUES (79, 26, 27, 3, 250, 'ACTIVE', '2022-12-12 17:48:36');
INSERT INTO final.tickets (id, user_id, exhibition_id, hall_id, price, state, operation_date)
VALUES (80, 26, 23, 1, 111, 'REFUNDED', '2023-01-13 22:09:21');
INSERT INTO final.tickets (id, user_id, exhibition_id, hall_id, price, state, operation_date)
VALUES (81, 26, 23, 1, 111, 'ACTIVE', '2023-01-13 22:09:26');

INSERT INTO final.users (id, login, password, first_name, last_name, email, balance, role)
VALUES (22, 'admin', '$2a$12$npRDbdvV50GM8fXuzO.T/OuqGGW7/T9sW7OqKNdAsMqYFUGRKKzsa', 'Адмін', 'Адмінов', 'admin@u.ua',
        40, 'ROLE_ADMIN');
INSERT INTO final.users (id, login, password, first_name, last_name, email, balance, role)
VALUES (26, 'kos', '$2a$12$J38.TlpqRYsUG6qgSXdDDO1y6VFJlGGrqT/Trso10XE16Y6mE4Y5C', 'Kostya', 'Kolom', 'inferno@ukr.net',
        1857, 'ROLE_USER');
INSERT INTO final.users (id, login, password, first_name, last_name, email, balance, role)
VALUES (30, 'serg', '$2a$12$oUcbuB.qLOs5ddjpDN6.hu5SuGjJ0iRASfN3hFdwc1ULwWvOnJ62.', 'Сергій', 'Стрижаков',
        'serg@ukr.net', 1889, 'ROLE_USER');
INSERT INTO final.users (id, login, password, first_name, last_name, email, balance, role)
VALUES (31, 'max', '$2a$12$t1klzo7lukHXkg6rqtc7qeRs2uXyOeUKwNkaAXlPY4VOrKloJxeoO', 'Maksim', 'Sergeev', 'max@ukr.net',
        3050, 'ROLE_USER');
INSERT INTO final.users (id, login, password, first_name, last_name, email, balance, role)
VALUES (32, 'ira', '$2a$12$3miaKOLhZEZVVpLEHEMQK.AKeqHW7FcnHZn4X21B.fahcHtvSjqxO', 'Irina', 'Stryzhakova',
        'ira@gmail.com', 610, 'ROLE_USER');
INSERT INTO final.users (id, login, password, first_name, last_name, email, balance, role)
VALUES (33, 'ivan', '$2a$12$9sSwn5qfhPfQhCdc3ZN9ye1JN6UfRkAWbwNb1w/TY1Gefewm8fb9C', 'Ivan', 'Ivaniv',
        'ivanov@gmail.com', 0, 'ROLE_USER');

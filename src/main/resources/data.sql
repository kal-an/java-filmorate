INSERT INTO user (login, name, email, birthday) VALUES ('vasya11', 'Vasya', 'vasya@mail.ru', '2001-10-10')
    INSERT INTO user (login, name, email, birthday) VALUES ('petya12', 'Petya', 'petya@mail.ru', '1990-10-10')

INSERT INTO friendship_status (status) VALUES ('CONFIRM')
INSERT INTO friend (user_id, friend_id, friendship_status_id) VALUES (1, 2, 1)

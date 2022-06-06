INSERT INTO MPA (name, description)
    VALUES ('G', 'у фильма нет возрастных ограничений');
INSERT INTO MPA (name, description)
    VALUES ('PG', 'детям рекомендуется смотреть фильм с родителями');
INSERT INTO MPA (name, description)
    VALUES ('PG-13', 'детям до 13 лет просмотр не желателен');
INSERT INTO MPA (name, description)
    VALUES ('R', 'лицам до 17 лет просматривать фильм можно только в присутствии взрослого');
INSERT INTO MPA (name, description)
    VALUES ('NC-17', 'лицам до 18 лет просмотр запрещён');

INSERT INTO GENRE (name) VALUES ( 'Комедия' );
INSERT INTO GENRE (name) VALUES ( 'Драма' );
INSERT INTO GENRE (name) VALUES ( 'Мультфильм' );
INSERT INTO GENRE (name) VALUES ( 'Триллер' );
INSERT INTO GENRE (name) VALUES ( 'Документальный' );
INSERT INTO GENRE (name) VALUES ( 'Боевик' );

INSERT INTO user (login, name, email, birthday)
    VALUES ( 'dolore', 'Nick Name', 'mail@mail.ru', '1946-08-20');
INSERT INTO user (login, name, email, birthday)
    VALUES ( 'friend', 'friend adipisicing', 'friend@mail.ru', '1976-08-20');
INSERT INTO user (login, name, email, birthday)
    VALUES ( 'common', 'col', 'friend@common.ru', '2000-08-20');

INSERT INTO friend (user_id, friend_id) VALUES ( '1', '2' );
INSERT INTO friend (user_id, friend_id) VALUES ( '1', '3' );
INSERT INTO friend (user_id, friend_id) VALUES ( '2', '3' );

INSERT INTO film (name, description, release_date, duration, rate, mpa_id)
    VALUES ( 'labore nulla', 'Duis in consequat esse', '1979-04-17',  '100', '4', '1');
INSERT INTO film (name, description, release_date, duration, rate, mpa_id)
    VALUES ( 'New film', 'New film about friends', '1999-04-30', '120', '4', '3');

INSERT INTO liked_film (USER_ID, FILM_ID) VALUES ( '1', '2' );
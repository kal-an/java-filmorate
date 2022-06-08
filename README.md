# Filmorate

1. добавление фильма;
2. обновление фильма;
3. получение всех фильмов;
4. создание пользователя;
5. обновление пользователя;
6. получение списка всех пользователей;
7. добавлять в друзья;
8. оценивать фильмы;
9. сохранение состояния данных после перезапуска.

## Описание базы-данных
![Filmorate DB](/db/Filmorate.png)
1. Получение всех фильмов 
```
"SELECT f.film_id, f.name, f.description, f.release_date, f.duration, " +
                "f.rate, m.mpa_id, m.name AS mpa_name, m.description AS mpa_description " +
                "FROM film AS f " +
                "INNER JOIN mpa AS m ON m.mpa_id = f.mpa_id"
```
2. Получение фильма по ID
```
"SELECT f.film_id, f.name, f.description, f.release_date, f.duration, " +
                "f.rate, m.mpa_id, m.name AS mpa_name, m.description AS mpa_description " +
                "FROM film AS f " +
                "INNER JOIN mpa AS m ON m.mpa_id = f.mpa_id " +
                "WHERE film_id = '1'"
```
3. Получение всех пользователей
```
"SELECT user_id, login, name, email, birthday FROM user"
```
4. Получение пользователя по ID
```
"SELECT user_id, login, name, email, birthday " +
                "FROM user WHERE user_id = ?"
```
5. Получение списка друзей
```
"SELECT f.friend_id AS user_id, u.login, u.name, u.email, u.birthday " +
                "FROM friend AS f " +
                "INNER JOIN user AS u ON f.friend_id = u.user_id " +
                "WHERE f.user_id = ?"
```
6. Получение списка общий друзей с другим пользователем
```
"SELECT f.friend_id AS user_id, u.login, u.name, u.email, u.birthday " +
                "    FROM friend AS f " +
                "    INNER JOIN user AS u ON f.friend_id = u.user_id " +
                "    WHERE f.user_id = ? " +
                "INTERSECT " +
                "SELECT f.friend_id AS user_id, u.login, u.name, u.email, u.birthday " +
                "    FROM friend AS f " +
                "    INNER JOIN user AS u ON f.friend_id = u.user_id " +
                "    WHERE f.user_id = ?"
```
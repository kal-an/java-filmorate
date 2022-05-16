# Filmorate

1. добавление фильма;
2. обновление фильма;
3. получение всех фильмов;
4. создание пользователя;
5. обновление пользователя;
6. получение списка всех пользователей;
7. добавлять в друзья;
8. оценивать фильмы.

## Описание базы-данных
/db/Filmorate.png
1. Получение всех фильмов 
SELECT * FROM film
2. Получение фильма по ID
SELECT * FROM film WHERE id = '1'
3. Получение всех пользователей
SELECT * FROM user
4. Получение пользователя по ID
SELECT * FROM user WHERE id = '1'
5. Получение списка друзей
SELECT u.name FROM user AS u 
INNER JOIN friend AS f ON u.user_id=f.user_id 
WHERE u.user_id='1'
6. Получение списка общий друзей с другим пользователем
SELECT u.name FROM user AS u INNER JOIN friend AS f ON u.user_id=f.user_id WHERE u.user_id=1
UNION
SELECT u.name FROM user AS u INNER JOIN friend AS f ON u.user_id=f.user_id WHERE u.user_id=3
7. Получение списка понравившихся пользователю фильмов
SELECT u.name FROM user AS u INNER JOIN liked_film AS lf ON u.user_id=lf.user_id WHERE u.user_id=1
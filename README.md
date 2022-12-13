# Filmorate :film_strip:

### Проект:
<p>REST Приложение для работы с фильмами и оценками пользователей.</p>

---
Фильмов очень много, с разными рейтингами и как же выбрать, какой посмотреть — воспользоваться сервисом !

---
### Возможности приложения:
1. добавление фильма;
```http request
POST /films
```
2. обновление фильма;
```http request
PUT /films
```
3. получение фильма по идентификатору;
```http request
GET /films
```
4. получение популярных фильмов;
```http request
GET /films/popular
```
5. оценить фильм;
```http request
PUT /films/{id}/like/{userId}
```
6. создание пользователя;
```http request
POST /users
```
7. обновление пользователя;
```http request
PUT /users
```
8. получение списка всех пользователей;
```http request
GET /users
```
9. получить пользователя по идентификатору;
```http request
GET /users/{id}
```
10. добавление в друзья;
```http request
PUT /users/{id}/friends/{friendId}
```
### Схема базы данных
![  Filmorate DB](/db/Filmorate.png)


---
### Стек технологий
+ [Java](https://www.java.com/)
+ [Spring Boot](https://spring.io/projects/spring-boot)
+ [H2 Database](https://www.h2database.com/html/main.html)
+ [JdbcTemplate](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/jdbc/core/JdbcTemplate.html)
+ [Apache Maven](https://maven.apache.org)
+ [Project Lombok](https://projectlombok.org)
+ [JUnit](https://junit.org/)
+ [Postman](https://www.postman.com)
+ [IntelliJ IDEA](https://www.jetbrains.com/ru-ru/idea/)
---
### Запуск приложения
Потребуется Java 11, Git, Apache Maven

1. Склонировать
```shell
git clone https://github.com/kal-an/java-filmorate
```
2. Собрать проект
```shell
mvn clean package
```
3. Запустить используя IDE, либо терминал
```shell
java -jar ./target/filmorate-0.0.1-SNAPSHOT.jar
```
---
### Выполнение тестирования
Подготовлены модульные тесты. Для запуска выполнить старт из среды разработки
```shell
src/test/java/ru/yandex/practicum/filmorate
```
Также подготовлены коллекции тестов, используя Postman
```shell
postman/sprint.json
```
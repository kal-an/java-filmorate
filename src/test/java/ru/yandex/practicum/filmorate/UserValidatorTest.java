package ru.yandex.practicum.filmorate;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.validators.ValidatorService;

import javax.validation.ConstraintViolationException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

class UserValidatorTest {

    private static ValidatorService validator;

    @BeforeAll
    public static void setUp() {
        validator = new ValidatorService();
    }

    @Test
    @DisplayName("Пользователь с корректными данными")
    public void createUser() {
        User user = User.builder()
                .login("dolore")
                .name("est adipisicing")
                .email("mail@mail.ru")
                .birthday(LocalDate.of(1946, 8, 20))
                .build();
        Assertions.assertTrue(validator.isValid(user));
    }

    @Test
    @DisplayName("Пользователь с некорректным логином")
    public void createUserBadLogin() {
        User user = User.builder()
                .login("dolore ullamco")
                .name("est adipisicing")
                .email("mail@mail.ru")
                .birthday(LocalDate.of(1946, 8, 20))
                .build();
        ConstraintViolationException ex = Assertions.assertThrows(
                ConstraintViolationException.class,
                () -> validator.isValid(user)
        );
        Assertions.assertEquals("login: Логин не может содержать пробелов.",
                ex.getMessage());
    }

    @Test
    @DisplayName("Пользователь с пустым логином")
    public void createUserEmptyLogin() {
        User user = User.builder()
                .login("")
                .name("est adipisicing")
                .email("mail@mail.ru")
                .birthday(LocalDate.of(1946, 8, 20))
                .build();
        ConstraintViolationException ex = Assertions.assertThrows(
                ConstraintViolationException.class,
                () -> validator.isValid(user)
        );
        Assertions.assertEquals("login: Логин не может содержать пробелов.",
                ex.getMessage());
    }

    @Test
    @DisplayName("Пользователь с значением логина null")
    public void createUserNullLogin() {
        User user = User.builder()
                .login(null)
                .name("est adipisicing")
                .email("mail@mail.ru")
                .birthday(LocalDate.of(1946, 8, 20))
                .build();
        ConstraintViolationException ex = Assertions.assertThrows(
                ConstraintViolationException.class,
                () -> validator.isValid(user)
        );
        Assertions.assertEquals("login: Логин не может быть пустым.", ex.getMessage());
    }

    @Test
    @DisplayName("Пользователь с некорректным email")
    public void createUserBadEmail() {
        User user = User.builder()
                .login("dolore")
                .name("est adipisicing")
                .email("mail.ru")
                .birthday(LocalDate.of(1946, 8, 20))
                .build();
        ConstraintViolationException ex = Assertions.assertThrows(
                ConstraintViolationException.class,
                () -> validator.isValid(user)
        );
        Assertions.assertEquals("login: Логин не может быть пустым.", ex.getMessage());
    }

    @Test
    @DisplayName("Пользователь с пустым email")
    public void createUserEmptyEmail() {
        User user = User.builder()
                .login("dolore")
                .name("est adipisicing")
                .email("")
                .birthday(LocalDate.of(1946, 8, 20))
                .build();
        ConstraintViolationException ex = Assertions.assertThrows(
                ConstraintViolationException.class,
                () -> validator.isValid(user)
        );
        Assertions.assertEquals("email: Адрес электронной почты некорректен.",
                ex.getMessage());
    }

    @Test
    @DisplayName("Пользователь с значением email null")
    public void createUserNullEmail() {
        User user = User.builder()
                .login("dolore")
                .name("est adipisicing")
                .email(null)
                .birthday(LocalDate.of(1946, 8, 20))
                .build();
        ConstraintViolationException ex = Assertions.assertThrows(
                ConstraintViolationException.class,
                () -> validator.isValid(user)
        );
        Assertions.assertEquals("email: Адрес электронной почты не может быть пустым.",
                ex.getMessage());
    }

    @Test
    @DisplayName("Пользователь с email из пробела")
    public void createUserSpaceEmail() {
        User user = User.builder()
                .login("dolore")
                .name("est adipisicing")
                .email(" ")
                .birthday(LocalDate.of(1946, 8, 20))
                .build();
        ConstraintViolationException ex = Assertions.assertThrows(
                ConstraintViolationException.class,
                () -> validator.isValid(user)
        );
        Assertions.assertEquals("email: Адрес электронной почты некорректен.",
                ex.getMessage());
    }

    @Test
    @DisplayName("Пользователь с днем рождения из будущего")
    public void createUserFutureBirthday() {
        User user = User.builder()
                .login("dolore")
                .name("est adipisicing")
                .email("mail@mail.ru")
                .birthday(LocalDate.of(2446, 8, 20))
                .build();
        ConstraintViolationException ex = Assertions.assertThrows(
                ConstraintViolationException.class,
                () -> validator.isValid(user)
        );
        Assertions.assertEquals("birthday: должно содержать прошедшую дату",
                ex.getMessage());
    }

    @Test
    @DisplayName("Пользователь с пустой датой рождения")
    public void createUserEmptyBirthday() {
        User user = User.builder()
                .login("dolore")
                .name("est adipisicing")
                .email("mail@mail.ru")
                .birthday(null)
                .build();
        ConstraintViolationException ex = Assertions.assertThrows(
                ConstraintViolationException.class,
                () -> validator.isValid(user)
        );
        Assertions.assertEquals("birthday: Дата рождения не может быть пустой.",
                ex.getMessage());
    }

    @Test
    @DisplayName("Пользователь с неверным форматом даты")
    public void createUserBadBirthday() {
        final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String json = "{\n" +
                "  \"login\": \"dolore\",\n" +
                "  \"name\": \"est adipisicing\",\n" +
                "  \"email\": \"mail@mail.ru\",\n" +
                "  \"birthday\": \"1946-30-30\"\n" +
                "}";
        JsonElement jsonElement = JsonParser.parseString(json);
        JsonObject jsonObject = jsonElement.getAsJsonObject();
        String birthday = jsonObject.get("birthday").getAsString();
        DateTimeParseException ex = Assertions.assertThrows(
                DateTimeParseException.class,
                () -> LocalDate.parse(birthday, formatter)
        );
        Assertions.assertEquals("Text '1946-30-30' could not be parsed: " +
                        "Invalid value for MonthOfYear (valid values 1 - 12): 30",
                ex.getMessage());
    }
}
package ru.yandex.practicum.filmorate;

import org.junit.jupiter.api.*;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.validators.ValidatorService;

import javax.validation.ConstraintViolationException;
import java.time.LocalDate;

class UserValidatorTest {

    private static ValidatorService validator;
    private static User user;

    @BeforeAll
    public static void setUp() {
        validator = new ValidatorService();
    }

    @BeforeEach
    @DisplayName("Создание сущности пользователь")
    public void createUser() {
        user = User.builder()
                .login("dolore")
                .name("est adipisicing")
                .email("mail@mail.ru")
                .birthday(LocalDate.of(1946, 8, 20))
                .build();
    }

    @Test
    @DisplayName("Пользователь с корректными данными")
    public void createUserGoodFields() {
        Assertions.assertTrue(validator.isValid(user));
    }

    @Test
    @DisplayName("Пользователь с пробелом в логине")
    public void createUserLoginWithSpace() {
        user.setLogin("dolore ullamco");
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
        user.setLogin("");
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
        user.setLogin(null);
        ConstraintViolationException ex = Assertions.assertThrows(
                ConstraintViolationException.class,
                () -> validator.isValid(user)
        );
        Assertions.assertEquals("login: Логин не может быть пустым.", ex.getMessage());
    }

    @Test
    @DisplayName("Пользователь с некорректным email")
    public void createUserBadEmail() {
        user.setEmail("mail.ru");
        ConstraintViolationException ex = Assertions.assertThrows(
                ConstraintViolationException.class,
                () -> validator.isValid(user)
        );
        Assertions.assertEquals("email: Адрес электронной почты некорректен.",
                ex.getMessage());
    }

    @Test
    @DisplayName("Пользователь с пустым email")
    public void createUserEmptyEmail() {
        user.setEmail("");
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
        user.setEmail(null);
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
        user.setEmail(" ");
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
        user.setBirthday(LocalDate.of(2446, 8, 20));
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
        user.setBirthday(null);
        ConstraintViolationException ex = Assertions.assertThrows(
                ConstraintViolationException.class,
                () -> validator.isValid(user)
        );
        Assertions.assertEquals("birthday: Дата рождения не может быть пустой.",
                ex.getMessage());
    }
}
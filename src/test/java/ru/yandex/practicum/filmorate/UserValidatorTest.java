package ru.yandex.practicum.filmorate;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.validators.ValidatorService;

import javax.validation.ConstraintViolationException;
import java.time.LocalDate;

class UserValidatorTest {

    private static ValidatorService validator;

    @BeforeAll
    public static void setUp() {
        validator = new ValidatorService();
    }

    @Test
    @DisplayName("Создание пользователя")
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
    @DisplayName("Создание пользователя с некорректным логином")
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
        Assertions.assertEquals(ConstraintViolationException.class, ex.getClass());
    }

    @Test
    @DisplayName("Создание пользователя с некорректным email")
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
        Assertions.assertEquals(ConstraintViolationException.class, ex.getClass());
    }

    @Test
    @DisplayName("Создание пользователя с некорректным датой рождения")
    public void createUserBadBirthday() {
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
        Assertions.assertEquals(ConstraintViolationException.class, ex.getClass());
    }
}
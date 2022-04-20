package ru.yandex.practicum.filmorate;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.validators.ValidatorService;

import javax.validation.ConstraintViolationException;
import java.time.LocalDate;

class FilmValidatorTest {

    private static ValidatorService validator;

    @BeforeAll
    public static void setUp() {
        validator = new ValidatorService();
    }

    @Test
    @DisplayName("Создание фильма")
    public void createFilm() {
        Film film = Film.builder()
                .name("nisi eiusmod")
                .description("adipisicing")
                .releaseDate(LocalDate.of(2007, 3, 25))
                .duration(100)
                .build();
        Assertions.assertTrue(validator.isValid(film));
    }

    @Test
    @DisplayName("Создание фильма с некорректными данными")
    public void failCreateUser() {
        Film film = Film.builder()
                .name("")
                .description("")
                .releaseDate(LocalDate.of(1900, 3, 25))
                .duration(-100)
                .build();
        ConstraintViolationException ex = Assertions.assertThrows(
                ConstraintViolationException.class,
                () -> validator.isValid(film)
        );
        Assertions.assertEquals(ConstraintViolationException.class, ex.getClass());
    }
}
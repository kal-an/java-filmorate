package ru.yandex.practicum.filmorate;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.validators.ValidatorService;

import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;
import java.time.LocalDate;

class FilmValidatorTest {

    private static ValidatorService validator;

    @BeforeAll
    public static void setUp() {
        validator = new ValidatorService();
    }

    @Test
    @DisplayName("Фильм с корректными данными")
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
    @DisplayName("Фильм с именем из пробела")
    public void createFilmEmptyName() {
        Film film = Film.builder()
                .name(" ")
                .description("adipisicing")
                .releaseDate(LocalDate.of(2007, 3, 25))
                .duration(100)
                .build();
        ConstraintViolationException ex = Assertions.assertThrows(
                ConstraintViolationException.class,
                () -> validator.isValid(film)
        );
        Assertions.assertEquals("name: Название фильма не может быть пустым",
                ex.getMessage());
    }

    @Test
    @DisplayName("Фильм с пустым именем")
    public void createFilmNullName() {
        Film film = Film.builder()
                .name(null)
                .description("adipisicing")
                .releaseDate(LocalDate.of(2007, 3, 25))
                .duration(100)
                .build();
        ConstraintViolationException ex = Assertions.assertThrows(
                ConstraintViolationException.class,
                () -> validator.isValid(film)
        );
        Assertions.assertEquals("name: Название фильма не может быть пустым, " +
                        "name: Название фильма не может быть пустым",
                ex.getMessage());
    }

    @Test
    @DisplayName("Фильм с описанием из пробела")
    public void createFilmEmptyDescription() {
        Film film = Film.builder()
                .name("nisi eiusmod")
                .description(" ")
                .releaseDate(LocalDate.of(2007, 3, 25))
                .duration(100)
                .build();
        ConstraintViolationException ex = Assertions.assertThrows(
                ConstraintViolationException.class,
                () -> validator.isValid(film)
        );
        Assertions.assertEquals("description: Описание фильма не может быть пустым",
                ex.getMessage());
    }

    @Test
    @DisplayName("Фильм с пустым описанием")
    public void createFilmNullDescription() {
        Film film = Film.builder()
                .name("nisi eiusmod")
                .description(null)
                .releaseDate(LocalDate.of(2007, 3, 25))
                .duration(100)
                .build();
        ConstraintViolationException ex = Assertions.assertThrows(
                ConstraintViolationException.class,
                () -> validator.isValid(film)
        );
        Assertions.assertEquals("description: Описание фильма не может быть пустым, " +
                        "description: Описание фильма не может быть пустым",
                ex.getMessage());
    }

    @Test
    @DisplayName("Фильм с длинным описанием")
    public void createFilmLongDescription() {
        Film film = Film.builder()
                .name("nisi eiusmod")
                .description("adipisicingadipisicingadipisicingadipisicingadipisicingadipisic" +
                        "ingadipisicingadipisicingadipisicingadipisicingadipisicingadipisicin" +
                        "gadipisicingadipisicingadipisicingadipisicinggadipisicingadipisicin" +
                        "gadipisicingadipisicing")
                .releaseDate(LocalDate.of(2007, 3, 25))
                .duration(100)
                .build();
        ConstraintViolationException ex = Assertions.assertThrows(
                ConstraintViolationException.class,
                () -> validator.isValid(film)
        );
        Assertions.assertEquals("description: Описание фильма " +
                        "не может быть длиннее 200 символов",
                ex.getMessage());
    }

    @Test
    @DisplayName("Фильм с ранней датой")
    public void createFilmEarlyDate() {
        Film film = Film.builder()
                .name("nisi eiusmod")
                .description("adipisicing")
                .releaseDate(LocalDate.of(1800, 3, 25))
                .duration(100)
                .build();
        ConstraintViolationException ex = Assertions.assertThrows(
                ConstraintViolationException.class,
                () -> validator.isValid(film)
        );
        Assertions.assertEquals("releaseDate: Дата должна быть " +
                        "не раньше 28 декабря 1895 года",
                ex.getMessage());
    }

    @Test
    @DisplayName("Фильм с днем рождения кино")
    public void createFilmBirthdayDate() {
        Film film = Film.builder()
                .name("nisi eiusmod")
                .description("adipisicing")
                .releaseDate(LocalDate.of(1895, 12, 28))
                .duration(100)
                .build();
        Assertions.assertTrue(validator.isValid(film));
    }

    @Test
    @DisplayName("Фильм с пустой датой")
    public void createFilmNullDate() {
        Film film = Film.builder()
                .name("nisi eiusmod")
                .description("adipisicing")
                .releaseDate(null)
                .duration(100)
                .build();
        ValidationException ex = Assertions.assertThrows(
                ValidationException.class,
                () -> validator.isValid(film)
        );
        Assertions.assertEquals(ValidationException.class, ex.getClass());
    }

    @Test
    @DisplayName("Фильм с отрицательной продолжительностью")
    public void createFilmNegativeDuration() {
        Film film = Film.builder()
                .name("")
                .description("adipisicing")
                .releaseDate(LocalDate.of(2007, 3, 25))
                .duration(-100)
                .build();
        ConstraintViolationException ex = Assertions.assertThrows(
                ConstraintViolationException.class,
                () -> validator.isValid(film)
        );
        Assertions.assertEquals(ConstraintViolationException.class, ex.getClass());
    }

    @Test
    @DisplayName("Фильм с нулевой продолжительностью")
    public void createFilmZeroDuration() {
        Film film = Film.builder()
                .name("nisi eiusmod")
                .description("adipisicing")
                .releaseDate(LocalDate.of(1900, 3, 25))
                .duration(0)
                .build();
        ConstraintViolationException ex = Assertions.assertThrows(
                ConstraintViolationException.class,
                () -> validator.isValid(film)
        );
        Assertions.assertEquals(ConstraintViolationException.class, ex.getClass());
    }

    @Test
    @DisplayName("Фильм с большой продолжительностью")
    public void createFilmDuration() {
        Film film = Film.builder()
                .name("nisi eiusmod")
                .description("adipisicing")
                .releaseDate(LocalDate.of(2007, 3, 25))
                .duration(50000000)
                .build();
        Assertions.assertTrue(validator.isValid(film));
    }
}
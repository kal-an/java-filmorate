package ru.yandex.practicum.filmorate;

import org.junit.jupiter.api.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.validators.ValidatorService;

import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;
import java.time.LocalDate;

class FilmValidatorTest {

    private static ValidatorService validator;
    public static Film film;

    @BeforeAll
    public static void setUp() {
        validator = new ValidatorService();
    }

    @BeforeEach
    @DisplayName("Создание сущности фильм")
    public void createFilm() {
        film = Film.builder()
                .name("nisi eiusmod")
                .description("adipisicing")
                .releaseDate(LocalDate.of(2007, 3, 25))
                .duration(100)
                .build();
    }

    @Test
    @DisplayName("Фильм с корректными данными")
    public void createFilmGoodFields() {
        Assertions.assertTrue(validator.isValid(film));
    }

    @Test
    @DisplayName("Фильм с именем из пробела")
    public void createFilmEmptyName() {
        film.setName(" ");
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
        film.setName(null);
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
        film.setDescription(" ");
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
        film.setDescription(null);
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
        film.setDescription("adipisicingadipisicingadipisicingadipisicingadipisicingadipisic" +
                "ingadipisicingadipisicingadipisicingadipisicingadipisicingadipisicin" +
                "gadipisicingadipisicingadipisicingadipisicinggadipisicingadipisicin" +
                "gadipisicingadipisicing");
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
        film.setReleaseDate(LocalDate.of(1800, 3, 25));
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
        film.setReleaseDate(LocalDate.of(1895, 12, 28));
        Assertions.assertTrue(validator.isValid(film));
    }

    @Test
    @DisplayName("Фильм с пустой датой")
    public void createFilmNullDate() {
        film.setReleaseDate(null);
        ValidationException ex = Assertions.assertThrows(
                ValidationException.class,
                () -> validator.isValid(film)
        );
        Assertions.assertEquals("HV000028: Unexpected exception during isValid call.",
                ex.getMessage());
    }

    @Test
    @DisplayName("Фильм с отрицательной продолжительностью")
    public void createFilmNegativeDuration() {
        film.setDuration(-100);
        ConstraintViolationException ex = Assertions.assertThrows(
                ConstraintViolationException.class,
                () -> validator.isValid(film)
        );
        Assertions.assertEquals("duration: Продолжительность фильма не может быть меньше 0",
                ex.getMessage());
    }

    @Test
    @DisplayName("Фильм с нулевой продолжительностью")
    public void createFilmZeroDuration() {
        film.setDuration(0);
        ConstraintViolationException ex = Assertions.assertThrows(
                ConstraintViolationException.class,
                () -> validator.isValid(film)
        );
        Assertions.assertEquals("duration: Продолжительность фильма не может быть меньше 0",
                ex.getMessage());
    }

    @Test
    @DisplayName("Фильм с большой продолжительностью")
    public void createFilmBigDuration() {
        film.setDuration(50000000);
        Assertions.assertTrue(validator.isValid(film));
    }
}
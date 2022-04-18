package ru.yandex.practicum.filmorate.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exceptions.InvalidFilmException;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@RestController
@Slf4j
public class FilmController {

    private final Map<Integer, Film> films = new HashMap<>();
    private static final int DESCRIPTION_LIMIT = 200;
    public static final LocalDate DATE_LIMIT = LocalDate.of(1895, 12, 28);
    private static int id;

    @PostMapping("/films")
    public void create(@RequestBody Film film) {
        if (film.getId() != null) {
            log.error(film.toString());
            throw new InvalidFilmException("Идентификатор должен быть пустым");
        }
        if (film.getName().isBlank() || film.getName() == null) {
            log.error(film.toString());
            throw new InvalidFilmException("Название фильма не может быть пустым");
        }
        if (film.getDescription().length() > DESCRIPTION_LIMIT) {
            log.error(film.toString());
            throw new InvalidFilmException("Описание фильма не может быть длиннее 200 символов");
        }
        if (film.getReleaseDate().isBefore(DATE_LIMIT)) {
            log.error(film.toString());
            throw new InvalidFilmException("Дата фильма не может быть раньше 28 декабря 1895 года");
        }
        if (film.getDuration() <= 0) {
            log.error(film.toString());
            throw new InvalidFilmException("Продолжительность фильма не может быть меньше 0");
        }
        film.setId(++id);
        log.debug(film.toString());
        films.put(id, film);
    }

    @PutMapping("/films")
    public void update(@RequestBody Film film) {
        if (film.getId() == null) {
            log.error(film.toString());
            throw new InvalidFilmException("Идентификатор не может быть пустым");
        }
        if (!films.containsKey(film.getId())) {
            log.error(film.toString());
            throw new InvalidFilmException("Идентификатор не корректен");
        }
        if (film.getName().isBlank() || film.getName() == null) {
            log.error(film.toString());
            throw new InvalidFilmException("Название фильма не может быть пустым");
        }
        if (film.getDescription().length() > DESCRIPTION_LIMIT) {
            log.error(film.toString());
            throw new InvalidFilmException("Описание фильма не может быть длиннее 200 символов");
        }
        if (film.getReleaseDate().isBefore(DATE_LIMIT)) {
            log.error(film.toString());
            throw new InvalidFilmException("Дата фильма не может быть раньше 28 декабря 1895 года");
        }
        if (film.getDuration() <= 0) {
            log.error(film.toString());
            throw new InvalidFilmException("Продолжительность фильма не может быть меньше 0");
        }
        log.debug(film.toString());
        films.put(film.getId(), film);
    }

    @GetMapping("/films")
    public Collection<Film> find() {
        log.debug("Текущее количество фильмов: {}", films.size());
        return films.values();
    }
}

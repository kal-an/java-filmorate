package ru.yandex.practicum.filmorate.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exceptions.InvalidFilmException;
import ru.yandex.practicum.filmorate.model.Film;

import javax.validation.Valid;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@RestController
@Slf4j
public class FilmController {

    private final Map<Integer, Film> films = new HashMap<>();
    private static int id;

    @PostMapping("/films")
    public void create(@Valid @RequestBody Film film) {
        if (film.getId() != null) {
            log.error(film.toString());
            throw new InvalidFilmException("Идентификатор должен быть пустым");
        }
        film.setId(++id);
        log.debug(film.toString());
        films.put(id, film);
    }

    @PutMapping("/films")
    public void update(@Valid @RequestBody Film film) {
        if (film.getId() == null) {
            log.error(film.toString());
            throw new InvalidFilmException("Идентификатор не может быть пустым");
        }
        if (!films.containsKey(film.getId())) {
            log.error(film.toString());
            throw new InvalidFilmException("Идентификатор не корректен");
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

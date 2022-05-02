package ru.yandex.practicum.filmorate.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exceptions.IncorrectParameterException;
import ru.yandex.practicum.filmorate.exceptions.InvalidEntityException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;

import javax.validation.Valid;
import java.util.Collection;

@RestController
@Slf4j
public class FilmController extends EntityController<Film> {

    private final FilmService service;

    @Autowired
    public FilmController(FilmService service) {
        this.service = service;
    }

    @PostMapping("/films")
    @Override
    public void create(@Valid @RequestBody Film film) {
        super.create(film);
        service.createFilm(film);
    }

    @PutMapping("/films")
    @Override
    public void update(@Valid @RequestBody Film film) {
        super.update(film);
        if (service.findFilmById(film.getId()) == null) {
            log.error(film.toString());
            throw new InvalidEntityException("Идентификатор некорректен");
        }
        service.updateFilm(film);
    }

    @GetMapping("/films")
    public Collection<Film> find() {
        return service.getAllFilms();
    }

    @GetMapping("/films/{id}")
    public Film getFilmById(@PathVariable int id) {
        return service.findFilmById(id);
    }

    @PutMapping("/films/{id}/like/{userId}")
    public void addLike(
            @PathVariable int id,
            @PathVariable int userId) {
        service.addLike(id, userId);
    }

    @DeleteMapping("/films/{id}/like/{userId}")
    public void deleteLike(
            @PathVariable int id,
            @PathVariable int userId) {
        service.deleteLike(id, userId);
    }

    @GetMapping("/films/popular")
    public Collection<Film> popularFilms(
            @RequestParam(defaultValue = "10", required = false) Integer count) {
        if (count < 0) {
            throw new IncorrectParameterException("count");
        }
        return service.getPopularFilm(count);
    }
}

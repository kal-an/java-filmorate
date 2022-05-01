package ru.yandex.practicum.filmorate.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
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
    }

    @PutMapping("/films")
    @Override
    public void update(@Valid @RequestBody Film film) {
        super.update(film);
    }

    @GetMapping("/films")
    @Override
    public Collection<Film> find() {
        Collection<Film> filmList = super.find();
        log.debug("Текущее количество фильмов: {}", filmList.size());
        return filmList;
    }
}

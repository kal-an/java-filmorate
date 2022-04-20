package ru.yandex.practicum.filmorate.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;

import javax.validation.Valid;
import java.util.Collection;

@RestController
@Slf4j
public class FilmController extends EntityController<Film> {

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
        return super.find();
    }
}

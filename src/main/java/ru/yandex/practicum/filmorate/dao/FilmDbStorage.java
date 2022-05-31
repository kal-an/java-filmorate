package ru.yandex.practicum.filmorate.dao;

import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;

import java.util.Collection;
import java.util.Optional;

public class FilmDbStorage implements FilmStorage {
    @Override
    public Optional<Film> create(Film entity) {
        return Optional.empty();
    }

    @Override
    public Collection<Film> getEntities() {
        return null;
    }

    @Override
    public Optional<Film> getEntity(Integer id) {
        return Optional.empty();
    }

    @Override
    public Optional<Film> update(Film entity) {
        return Optional.empty();
    }

    @Override
    public void delete(Integer id) {

    }
}

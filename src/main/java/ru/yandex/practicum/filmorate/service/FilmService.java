package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;
import ru.yandex.practicum.filmorate.storage.film.InMemoryFilmStorage;

import java.util.Collection;

@Service
public class FilmService {

    private final FilmStorage storage;

    @Autowired
    public FilmService(InMemoryFilmStorage storage) {
        this.storage = storage;
    }

    public void addToStorage(Film film) {
        storage.create(film);
    }

    public void updateInStorage(Film film) {
        storage.update(film);
    }

    public void deleteFromStorage(Integer id) {
        storage.delete(id);
    }

    public Collection<Film> getFromStorage() {
        return storage.getEntities();
    }

    public Film findFilmById(Integer id) {
        return storage.getEntity(id);
    }

    public void addLike() {

    }

    public void deleteLike() {

    }
}

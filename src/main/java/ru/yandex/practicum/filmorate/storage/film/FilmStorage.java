package ru.yandex.practicum.filmorate.storage.film;

import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.EntityStorage;

import java.util.Collection;

public interface FilmStorage extends EntityStorage<Film> {

    Collection<Film> getPopularFilm(Integer size);
}

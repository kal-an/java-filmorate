package ru.yandex.practicum.filmorate.storage.film;

import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.EntityStorage;

import java.util.List;

public interface FilmStorage extends EntityStorage<Film> {

    List<Film> getPopularFilm(Integer size);
}

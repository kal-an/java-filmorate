package ru.yandex.practicum.filmorate.dao;

import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.EntityStorage;

import java.util.List;

public interface FilmDao extends EntityStorage<Film> {

    List<Film> getPopularFilm(Integer size);
}

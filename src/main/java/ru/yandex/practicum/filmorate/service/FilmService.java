package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exceptions.FilmNotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;

import java.util.Collection;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class FilmService {

    private final FilmStorage storage;
    private final UserService userService;

    @Autowired
    public FilmService(FilmStorage storage, UserService userService) {
        this.storage = storage;
        this.userService = userService;
    }

    public void createFilm(Film film) {
        storage.create(film);
        log.info(String.format("Добавлен фильм %s", film));
    }

    public Film updateFilm(Film film) {
        storage.update(film);
        log.info(String.format("Обновлен фильм %s", film));
        return film;
    }

    public Collection<Film> getAllFilms() {
        return storage.getEntities();
    }

    public Film findFilmById(Integer id) {
        final Film film = storage.getEntity(id);
        if (film == null) {
            throw new FilmNotFoundException(String.format("Фильм c ID %s не найден", id));
        }
        return film;
    }

    public void addLike(Integer filmId, Integer userId) {
        final User user = userService.findUserById(userId);
        final Film film = findFilmById(filmId);
        if (user.getLikedFilms() == null) {
            user.setLikedFilms(new HashSet<>());
            log.info(String.format("Фильм %s без лайков", film));
        }
        user.getLikedFilms().add(filmId);
        film.setRate(film.getRate() + 1);
        userService.updateUser(user);
        updateFilm(film);
        log.info(String.format("К фильму %s добавлен like от пользователя %s", film, user));
    }

    public void deleteLike(Integer filmId, Integer userId) {
        final User user = userService.findUserById(userId);
        final Film film = findFilmById(filmId);
        if (user.getLikedFilms() == null) {
            user.setLikedFilms(new HashSet<>());
            log.info(String.format("Фильм %s без лайков", film));
        }
        user.getLikedFilms().remove(filmId);
        film.setRate(film.getRate() - 1);
        userService.updateUser(user);
        updateFilm(film);
        log.info(String.format("У фильма %s удален like от пользователя %s", film, user));
    }

    public List<Film> getPopularFilm(Integer size) {
        return storage.getEntities()
                .stream()
                .sorted(Comparator.comparing(Film::getRate).reversed())
                .limit(size)
                .collect(Collectors.toList());
    }
}

package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exceptions.FilmNotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;

import java.util.*;
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

    public Optional<Film> createFilm(Film film) {
        log.info(String.format("Добавлен фильм %s", film));
        return storage.create(film);
    }

    public Optional<Film> updateFilm(Film film) {
        log.info(String.format("Обновлен фильм %s", film));
        return storage.update(film);
    }

    public Collection<Film> getAllFilms() {
        return storage.getEntities();
    }

    public Optional<Film> findFilmById(Integer id) {
        final Optional<Film> optionalFilm = storage.getEntity(id);
        if (optionalFilm.isEmpty()) {
            throw new FilmNotFoundException(String.format("Фильм c ID %s не найден", id));
        }
        return optionalFilm;
    }

    public void addLike(Integer filmId, Integer userId) {
        final Optional<User> optionalUser = userService.findUserById(userId);
        final Optional<Film> optionalFilm = findFilmById(filmId);
        if (optionalUser.isPresent()) {
            final User user = optionalUser.get();
            if (user.getLikedFilms() == null) {
                user.setLikedFilms(new HashSet<>());
            }
            user.getLikedFilms().add(filmId);
            userService.updateUser(user);
            log.info(String.format("Пользователь %s поставил like", user));
        }
        if (optionalFilm.isPresent()) {
            final Film film = optionalFilm.get();
            film.setRate(film.getRate() + 1);
            updateFilm(film);
            log.info(String.format("К фильму %s добавлен like", film));
        }
    }

    public void deleteLike(Integer filmId, Integer userId) {
        final Optional<User> optionalUser = userService.findUserById(userId);
        final Optional<Film> optionalFilm = findFilmById(filmId);
        if (optionalUser.isPresent()) {
            final User user = optionalUser.get();
            if (user.getLikedFilms() == null) {
                user.setLikedFilms(new HashSet<>());
            }
            user.getLikedFilms().remove(filmId);
            userService.updateUser(user);
            log.info(String.format("Пользователь %s удалил like", user));
        }
        if (optionalFilm.isPresent()) {
            final Film film = optionalFilm.get();
            film.setRate(film.getRate() - 1);
            updateFilm(film);
            log.info(String.format("У фильма %s удален like", film));
        }
    }

    public List<Film> getPopularFilm(Integer size) {
        return storage.getEntities()
                .stream()
                .sorted(Comparator.comparing(Film::getRate).reversed())
                .limit(size)
                .collect(Collectors.toList());
    }
}

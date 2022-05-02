package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exceptions.FilmNotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;

import java.util.Collection;
import java.util.Comparator;
import java.util.Set;
import java.util.stream.Collectors;

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
    }

    public void updateFilm(Film film) {
        storage.update(film);
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
        final Set<Integer> likedFilms = user.getLikedFilms();
        likedFilms.add(filmId);
        film.setLikedCount(film.getLikedCount() + 1);
        userService.updateUser(user);
        updateFilm(film);
    }

    public void deleteLike(Integer filmId, Integer userId) {
        final User user = userService.findUserById(userId);
        final Film film = findFilmById(filmId);
        final Set<Integer> likedFilms = user.getLikedFilms();
        likedFilms.remove(filmId);
        film.setLikedCount(film.getLikedCount() - 1);
        userService.updateUser(user);
        updateFilm(film);
    }

    public Collection<Film> getPopularFilm(Integer size) {
        return storage.getEntities()
                .stream()
                .sorted(Comparator.comparing(Film::getLikedCount).reversed())
                .limit(size)
                .collect(Collectors.toList());
    }
}

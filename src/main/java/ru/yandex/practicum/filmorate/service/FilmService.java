package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.dao.FilmDao;
import ru.yandex.practicum.filmorate.exceptions.FilmNotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class FilmService {

    private final FilmStorage storage;
    private final FilmDao filmDao;
    private final UserService userService;

    @Autowired
    public FilmService(@Qualifier("filmDaoImpl") FilmStorage storage,
                       FilmDao filmDao, UserService userService) {
        this.storage = storage;
        this.filmDao = filmDao;
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
        if (optionalUser.isPresent() && optionalFilm.isPresent()) {
            final User user = optionalUser.get();
            final Film film = optionalFilm.get();
            userService.addLike(userId, filmId);
            film.setRate(film.getRate() + 1);
            updateFilm(film);
            log.info("Пользователь {} поставить лайк фильму {} ", user, film);
        }
    }

    public void deleteLike(Integer filmId, Integer userId) {
        final Optional<User> optionalUser = userService.findUserById(userId);
        final Optional<Film> optionalFilm = findFilmById(filmId);
        if (optionalUser.isPresent() && optionalFilm.isPresent()) {
            final User user = optionalUser.get();
            final Film film = optionalFilm.get();
            userService.deleteLike(userId, filmId);
            film.setRate(film.getRate() - 1);
            updateFilm(film);
            log.info("Пользователь {} удалил лайк у фильма {} ", user, film);
        }
    }

    public List<Film> getPopularFilm(Integer size) {
        return filmDao.getPopularFilm(size);
    }
}

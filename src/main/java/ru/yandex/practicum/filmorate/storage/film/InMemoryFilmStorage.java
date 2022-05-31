package ru.yandex.practicum.filmorate.storage.film;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Component
public class InMemoryFilmStorage implements FilmStorage {

    private final Map<Integer, Film> films = new HashMap<>();

    @Override
    public Optional<Film> create(Film film) {
        int index = films.size() + 1;
        film.setId(index);
        log.debug(film.toString());
        films.put(index, film);
        return Optional.of(film);
    }

    @Override
    public Collection<Film> getEntities() {
        return films.values();
    }

    @Override
    public Optional<Film> getEntity(Integer id) {
        return Optional.of(films.get(id));
    }

    @Override
    public Optional<Film> update(Film film) {
        log.debug(film.toString());
        films.put(film.getId(), film);
        return Optional.of(film);
    }

    @Override
    public void delete(Integer id) {
        films.remove(id);
    }
}

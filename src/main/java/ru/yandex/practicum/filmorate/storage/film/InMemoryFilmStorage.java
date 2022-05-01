package ru.yandex.practicum.filmorate.storage.film;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
public class InMemoryFilmStorage implements FilmStorage {

    private final Map<Integer, Film> films = new HashMap<>();

    @Override
    public void create(Film entity) {
        int index = films.size() + 1;
        entity.setId(index);
        log.debug(entity.toString());
        films.put(index, entity);
    }

    @Override
    public Collection<Film> find() {
        return films.values();
    }

    @Override
    public void update(Film entity) {
        log.debug(entity.toString());
        films.put(entity.getId(), entity);
    }

    @Override
    public void delete(Integer id) {
        films.remove(id);
    }
}

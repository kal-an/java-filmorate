package ru.yandex.practicum.filmorate.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestBody;
import ru.yandex.practicum.filmorate.exceptions.InvalidFilmException;
import ru.yandex.practicum.filmorate.model.Entity;

import javax.validation.Valid;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Slf4j
abstract class EntityController<T extends Entity> {

    private final Map<Integer, T> entities = new HashMap<>();

    public void create(@Valid @RequestBody T entity) {
        if (entity.getId() != null) {
            log.error(entity.toString());
            throw new InvalidFilmException("Идентификатор должен быть пустым");
        }
        int index = entities.size() + 1;
        entity.setId(index);
        log.debug(entity.toString());
        entities.put(index, entity);
    }

    public void update(@Valid @RequestBody T entity) {
        if (entity.getId() == null) {
            log.error(entity.toString());
            throw new InvalidFilmException("Идентификатор не может быть пустым");
        }
        if (!entities.containsKey(entity.getId())) {
            log.error(entity.toString());
            throw new InvalidFilmException("Идентификатор не корректен");
        }
        log.debug(entity.toString());
        entities.put(entity.getId(), entity);
    }

    public Collection<T> find() {
        return entities.values();
    }
}

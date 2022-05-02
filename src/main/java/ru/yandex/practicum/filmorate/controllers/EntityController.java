package ru.yandex.practicum.filmorate.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestBody;
import ru.yandex.practicum.filmorate.exceptions.InvalidEntityException;
import ru.yandex.practicum.filmorate.model.Entity;

import javax.validation.Valid;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Slf4j
abstract class EntityController<T extends Entity> {

    public T create(@Valid @RequestBody T entity) {
        if (entity.getId() != null) {
            log.error(entity.toString());
            throw new InvalidEntityException("Идентификатор должен быть пустым");
        }
        return entity;
    }

    public void update(@Valid @RequestBody T entity) {
        if (entity.getId() == null) {
            log.error(entity.toString());
            throw new InvalidEntityException("Идентификатор не может быть пустым");
        }
    }
}

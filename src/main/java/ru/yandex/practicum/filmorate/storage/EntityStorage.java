package ru.yandex.practicum.filmorate.storage;

import java.util.Collection;
import java.util.Optional;

public interface EntityStorage<T> {

    Optional<T> create(T entity);

    Collection<T> getEntities();

    Optional<T> getEntity(Integer id);

    Optional<T> update(T entity);

    void delete(Integer id);
}

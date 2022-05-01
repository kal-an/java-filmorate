package ru.yandex.practicum.filmorate.storage;

import java.util.Collection;

public interface EntityStorage<T> {

    void create(T entity);

    Collection<T> find();

    void update(T entity);

    void delete(Integer id);
}

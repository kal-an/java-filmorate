package ru.yandex.practicum.filmorate.storage;

import java.util.Collection;

public interface EntityStorage<T> {

    T create(T entity);

    Collection<T> find();

    T update(T entity);

    void delete(Integer id);
}

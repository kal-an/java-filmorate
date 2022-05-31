package ru.yandex.practicum.filmorate.dao;

import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.Collection;

@Repository
public class UserDbStorage implements UserStorage {
    @Override
    public User create(User entity) {
        return null;
    }

    @Override
    public Collection<User> getEntities() {
        return null;
    }

    @Override
    public User getEntity(Integer id) {
        return null;
    }

    @Override
    public User update(User entity) {
        return null;
    }

    @Override
    public void delete(Integer id) {

    }
}

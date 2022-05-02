package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.InMemoryUserStorage;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.Collection;

@Service
public class UserService {

    private final UserStorage storage;

    @Autowired
    public UserService(InMemoryUserStorage storage) {
        this.storage = storage;
    }

    public void addToStorage(User user) {
        storage.create(user);
    }

    public void updateInStorage(User user) {
        storage.update(user);
    }

    public Collection<User> getFromStorage() {
        return storage.getEntities();
    }

    public User findUserById(Integer id) {
        return storage.getEntity(id);
    }

    public void addFriend() {

    }

    public void deleteFriend() {

    }

    public void getCommonFriends() {

    }
}

package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exceptions.UserNotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class UserService {

    private final UserStorage storage;

    @Autowired
    public UserService(@Qualifier("userDbStorage") UserStorage storage) {
        this.storage = storage;
    }

    public Optional<User> createUser(User user) {
        log.info(String.format("Добавлен пользователь %s", user));
        return storage.create(user);
    }

    public Optional<User> updateUser(User user) {
        log.info(String.format("Обновлен пользователь %s", user));
        return storage.update(user);
    }

    public Collection<User> getAllUsers() {
        return storage.getEntities();
    }

    public Optional<User> findUserById(Integer id) {
        final Optional<User> optionalUser = storage.getEntity(id);
        if (optionalUser.isEmpty()) {
            throw new UserNotFoundException(String.format("Пользователь c ID %s не найден", id));
        }
        return storage.getEntity(id);
    }

    public void addFriend(Integer id, Integer friendId) {
        final Optional<User> optionalUser = findUserById(id);
        final Optional<User> optionalFriend = findUserById(friendId);
        if (optionalUser.isPresent() && optionalFriend.isPresent()) {
            storage.addFriend(id, friendId);
            log.info("Пользователь {} добавил друга {}", optionalUser.get(), optionalFriend.get());
        }
    }

    public void deleteFriend(Integer id, Integer friendId) {
        final Optional<User> optionalUser = findUserById(id);
        final Optional<User> optionalFriend = findUserById(friendId);
        if (optionalUser.isPresent() && optionalFriend.isPresent()) {
            storage.deleteFriend(id, friendId);
            log.info("Пользователь {} удалил друга {}", optionalUser.get(), optionalFriend.get());
        }
    }

    public List<User> getUserFriends(Integer id) {
        final Optional<User> optionalUser = findUserById(id);
        if (optionalUser.isPresent()) {
            return storage.getUserFriends(id);
        }
        return List.of();
    }

    public List<User> getCommonFriends(Integer id, Integer otherId) {
        final Optional<User> optionalFirstUser = findUserById(id);
        final Optional<User> optionalSecondUser = findUserById(otherId);
        if (optionalFirstUser.isPresent() && optionalSecondUser.isPresent()) {
            return storage.getCommonFriends(id, otherId);
        }
        return List.of();
    }

    public void addLike(Integer userId, Integer filmId) {
        storage.addLike(userId, filmId);
    }

    public void deleteLike(Integer userId, Integer filmId) {
        storage.deleteLike(userId, filmId);
    }
}

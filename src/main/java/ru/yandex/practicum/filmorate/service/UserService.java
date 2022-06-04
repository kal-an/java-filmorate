package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.dao.UserDao;
import ru.yandex.practicum.filmorate.exceptions.UserNotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.*;

@Slf4j
@Service
public class UserService {

    private final UserStorage storage;
    private final UserDao userDao;

    @Autowired
    public UserService(@Qualifier("userDaoImpl") UserStorage storage, UserDao userDao) {
        this.storage = storage;
        this.userDao = userDao;
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
            userDao.deleteFriend(id, friendId);
            log.info("Пользователь {} добавил друга {}", optionalUser.get(), optionalFriend.get());
        }
    }

    public void deleteFriend(Integer id, Integer friendId) {
        final Optional<User> optionalUser = findUserById(id);
        final Optional<User> optionalFriend = findUserById(friendId);
        if (optionalUser.isPresent() && optionalFriend.isPresent()) {
            userDao.deleteFriend(id, friendId);
            log.info("Пользователь {} удалил друга {}", optionalUser.get(), optionalFriend.get());
        }
    }

    public List<User> getUserFriends(Integer id) {
        final Optional<User> optionalUser = findUserById(id);
        if (optionalUser.isPresent()) {
            return userDao.getUserFriends(id);
        }
        return List.of();
    }

    public List<User> getCommonFriends(Integer id, Integer otherId) {
        final Optional<User> optionalFirstUser = findUserById(id);
        final Optional<User> optionalSecondUser = findUserById(otherId);
        if (optionalFirstUser.isPresent() && optionalSecondUser.isPresent()) {
            return userDao.getCommonFriends(id, otherId);
        }
        return List.of();
    }

}

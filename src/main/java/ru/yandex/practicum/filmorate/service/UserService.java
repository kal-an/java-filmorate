package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exceptions.UserNotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.InMemoryUserStorage;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
public class UserService {

    private final UserStorage storage;

    @Autowired
    public UserService(UserStorage storage) {
        this.storage = storage;
    }

    public void createUser(User user) {
        storage.create(user);
        log.info(String.format("Добавлен пользователь %s", user));
    }

    public void updateUser(User user) {
        storage.update(user);
        log.info(String.format("Обновлен пользователь %s", user));
    }

    public Collection<User> getAllUsers() {
        return storage.getEntities();
    }

    public User findUserById(Integer id) {
        final User user = storage.getEntity(id);
        if (user == null) {
            throw new UserNotFoundException(String.format("Пользователь c ID %s не найден", id));
        }
        return storage.getEntity(id);
    }

    public void addFriend(Integer id, Integer friendId) {
        final User user = findUserById(id);
        final User friend = findUserById(friendId);
        if (user.getFriends() == null) {
            user.setFriends(new HashSet<>());
            log.info(String.format("Пользователь %s без друзей", user));
        }
        if (friend.getFriends() == null) {
            friend.setFriends(new HashSet<>());
            log.info(String.format("Пользователь %s без друзей", friend));
        }
        user.getFriends().add(friendId);
        friend.getFriends().add(id);
        updateUser(user);
        updateUser(friend);
        log.info(String.format("К пользователю %s, добавлен друг с ID %s", user, friendId));
    }

    public void deleteFriend(Integer id, Integer friendId) {
        final User user = findUserById(id);
        final User friend = findUserById(friendId);
        if (user.getFriends() == null) {
            user.setFriends(new HashSet<>());
            log.info(String.format("Пользователь %s без друзей", user));
        }
        if (friend.getFriends() == null) {
            friend.setFriends(new HashSet<>());
            log.info(String.format("Пользователь %s без друзей", friend));
        }
        user.getFriends().remove(friendId);
        friend.getFriends().remove(id);
        updateUser(user);
        updateUser(friend);
        log.info(String.format("У пользователя %s, удален друг с ID %s", user, friendId));
    }

    public List<User> getUserFriends(Integer id) {
        final User user = findUserById(id);
        return user.getFriends().stream()
                .map(this::findUserById)
                .collect(Collectors.toList());
    }

    public List<User> getCommonFriends(Integer id, Integer otherId) {
        User firstUser = findUserById(id);
        User secondUser = findUserById(otherId);
        Set<Integer> firstUserFriends = firstUser.getFriends();
        Set<Integer> secondUserFriends = secondUser.getFriends();
        Set<Integer> commonFriendsId = new HashSet<>(firstUserFriends);
        commonFriendsId.retainAll(secondUserFriends);
        return commonFriendsId.stream()
                .map(this::findUserById)
                .collect(Collectors.toList());
    }

}

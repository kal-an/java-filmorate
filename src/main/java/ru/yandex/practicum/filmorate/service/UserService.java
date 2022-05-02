package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exceptions.UserNotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.InMemoryUserStorage;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserStorage storage;

    @Autowired
    public UserService(InMemoryUserStorage storage) {
        this.storage = storage;
    }

    public void createUser(User user) {
        storage.create(user);
    }

    public void updateUser(User user) {
        storage.update(user);
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
        findUserById(friendId);
        user.getFriends().add(friendId);
        updateUser(user);
    }

    public void deleteFriend(Integer id, Integer friendId) {
        final User user = findUserById(id);
        findUserById(friendId);
        user.getFriends().remove(friendId);
        updateUser(user);
    }

    public Collection<User> getUserFriends(Integer id) {
        final User user = findUserById(id);
        return user.getFriends().stream()
                .map(this::findUserById)
                .collect(Collectors.toList());
    }

    public Collection<User> getCommonFriends(Integer id, Integer otherId) {
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

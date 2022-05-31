package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.dao.FriendDao;
import ru.yandex.practicum.filmorate.exceptions.UserNotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class UserService {

    private final UserStorage storage;
    private final FriendDao friendDao;

    @Autowired
    public UserService(@Qualifier("userDbStorage") UserStorage storage, FriendDao friendDao) {
        this.storage = storage;
        this.friendDao = friendDao;
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
        if (optionalUser.isPresent()) {
            final User user = optionalUser.get();
            user.getFriends().add(friendId);
            updateUser(user);
        }
        if (optionalFriend.isPresent()) {
            final User friend = optionalFriend.get();
            friend.getFriends().add(friendId);
            updateUser(friend);
        }
    }

    public void deleteFriend(Integer id, Integer friendId) {
        final Optional<User> optionalUser = findUserById(id);
        final Optional<User> optionalFriend = findUserById(friendId);
        if (optionalUser.isPresent()) {
            final User user = optionalUser.get();
            user.getFriends().remove(friendId);
            updateUser(user);
        }
        if (optionalFriend.isPresent()) {
            final User friend = optionalFriend.get();
            friend.getFriends().remove(id);
            updateUser(friend);
        }
    }

    public List<User> getUserFriends(Integer id) {
        final Optional<User> optionalUser = findUserById(id);
        if (optionalUser.isPresent()) {
            return friendDao.getFriendsId(id).stream()
                    .map(this::findUserById)
                    .filter(Optional::isPresent)
                    .map(Optional::get)
                    .collect(Collectors.toList());
        }
        return List.of();
    }

    public List<User> getCommonFriends(Integer id, Integer otherId) {
        final Optional<User> optionalFirstUser = findUserById(id);
        final Optional<User> optionalSecondUser = findUserById(otherId);
        Set<Integer> firstUserFriends = new HashSet<>();
        Set<Integer> secondUserFriends = new HashSet<>();
        if (optionalFirstUser.isPresent()) {
            firstUserFriends = optionalFirstUser.get().getFriends();
        }
        if (optionalSecondUser.isPresent()) {
            secondUserFriends = optionalSecondUser.get().getFriends();
        }
        Set<Integer> commonFriendsId = new HashSet<>(firstUserFriends);
        commonFriendsId.retainAll(secondUserFriends);
        return commonFriendsId.stream()
                .map(this::findUserById)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());
    }

}

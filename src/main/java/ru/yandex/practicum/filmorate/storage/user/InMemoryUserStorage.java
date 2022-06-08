package ru.yandex.practicum.filmorate.storage.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Component
public class InMemoryUserStorage implements UserStorage {

    private final Map<Integer, User> users = new HashMap<>();

    @Override
    public Optional<User> create(User user) {
        int index = users.size() + 1;
        user.setId(index);
        log.debug(user.toString());
        users.put(index, user);
        return Optional.of(user);
    }

    @Override
    public Collection<User> getEntities() {
        return users.values();
    }

    @Override
    public Optional<User> getEntity(Integer id) {
        return Optional.of(users.get(id));
    }

    @Override
    public Optional<User> update(User user) {
        log.debug(user.toString());
        users.put(user.getId(), user);
        return Optional.of(user);
    }

    @Override
    public void delete(Integer id) {
        users.remove(id);
    }

    @Override
    public Collection<User> getUserFriends(Integer id) {
        return users.get(id).getFriends().stream()
                .map(this::getEntity)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());
    }

    @Override
    public Collection<User> getCommonFriends(Integer id, Integer otherId) {
        Optional<User> optionalUser = getEntity(id);
        Optional<User> optionalSecondUser = getEntity(otherId);
        if (optionalUser.isPresent() && optionalSecondUser.isPresent()) {
            Set<Integer> firstUserFriends = optionalUser.get().getFriends();
            Set<Integer> secondUserFriends = optionalSecondUser.get().getFriends();
            Set<Integer> commonFriendsId = new HashSet<>(firstUserFriends);
            commonFriendsId.retainAll(secondUserFriends);
            return commonFriendsId.stream()
                    .map(this::getEntity)
                    .filter(Optional::isPresent)
                    .map(Optional::get)
                    .collect(Collectors.toList());
        }
        return List.of();
    }

    @Override
    public void deleteFriend(Integer id, Integer friendId) {
        users.get(id).getFriends().remove(friendId);
    }

    @Override
    public void addFriend(Integer id, Integer friendId) {
        users.get(id).getFriends().add(friendId);
    }

    @Override
    public void addLike(Integer userId, Integer filmId) {
        users.get(userId).getLikedFilms().add(filmId);
    }

    @Override
    public void deleteLike(Integer userId, Integer filmId) {
        users.get(userId).getLikedFilms().remove(filmId);
    }
}

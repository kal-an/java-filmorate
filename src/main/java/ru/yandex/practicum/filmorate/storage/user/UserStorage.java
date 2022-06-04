package ru.yandex.practicum.filmorate.storage.user;

import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.EntityStorage;

import java.util.List;

public interface UserStorage extends EntityStorage<User> {

    List<User> getUserFriends(Integer id);

    List<User> getCommonFriends(Integer id, Integer otherId);

    void deleteFriend(Integer id, Integer otherId);

    void addFriend(Integer id, Integer otherId);

    void addLike(Integer userId, Integer filmId);

    void deleteLike(Integer userId, Integer filmId);
}

package ru.yandex.practicum.filmorate.dao;

import ru.yandex.practicum.filmorate.model.Like;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.EntityStorage;

import java.util.List;

public interface UserDao extends EntityStorage<User> {

    List<User> getUserFriends(Integer id);

    List<User> getCommonFriends(Integer id, Integer otherId);

    void deleteFriend(Integer id, Integer otherId);

    Like addLike(Integer userId, Integer filmId);

    void deleteLike(Integer userId, Integer filmId);
}

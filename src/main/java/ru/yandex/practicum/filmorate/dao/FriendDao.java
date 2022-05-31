package ru.yandex.practicum.filmorate.dao;

import java.util.Set;

public interface FriendDao {

    Set<Integer> getFriendsId(Integer id);
}

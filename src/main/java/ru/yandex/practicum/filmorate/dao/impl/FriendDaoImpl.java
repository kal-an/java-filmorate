package ru.yandex.practicum.filmorate.dao.impl;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.dao.FriendDao;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

@Repository
public class FriendDaoImpl implements FriendDao {

    private final JdbcTemplate jdbcTemplate;
    private final UserStorage userStorage;

    public FriendDaoImpl(JdbcTemplate jdbcTemplate, @Qualifier("userDbStorage") UserStorage userStorage) {
        this.jdbcTemplate = jdbcTemplate;
        this.userStorage = userStorage;
    }

    @Override
    public Set<Integer> getFriendsId(Integer id) {
        String sql = "SELECT friend_id FROM friend AS f " +
                "INNER JOIN user AS u ON f.user_id = u.user_id " +
                "WHERE u.user_id = ?";
        return new HashSet<>(jdbcTemplate.query(sql, (rs, rowNum) -> makeFriendsId(rs), id));
    }

    private Integer makeFriendsId(ResultSet rs) throws SQLException {
        return rs.getInt("friend_id");
    }
}

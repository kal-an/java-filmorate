package ru.yandex.practicum.filmorate.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.exceptions.UserNotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Collection;

@Repository
public class UserDbStorage implements UserStorage {

    private final JdbcTemplate jdbcTemplate;
    private final Logger log = LoggerFactory.getLogger(UserDbStorage.class);

    @Autowired
    public UserDbStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public User create(User entity) {
        String sql = "INSERT INTO user (login, name, email, birthday) VALUES (?, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement stmt = connection.prepareStatement(sql, new String[]{"user_id"});
            stmt.setString(1, entity.getLogin());
            stmt.setString(2, entity.getName());
            stmt.setString(3, entity.getEmail());
            stmt.setDate(4, Date.valueOf(entity.getBirthday()));
            return stmt;
        }, keyHolder);
        return getEntity(keyHolder.getKey().intValue());
    }

    @Override
    public Collection<User> getEntities() {
        return null;
    }

    @Override
    public User getEntity(Integer id) {
        String sql = "SELECT * FROM user WHERE user_id = ?";
        User user = jdbcTemplate.queryForObject(sql, (rs, rowNum) -> makeUser(rs), id);
        if (user == null) {
            throw new UserNotFoundException(String.format("Пользователь c ID %s не найден", id));
        }
        log.info("Найден пользователь: {} {}", user.getId(), user.getName());
        return user;
    }

    @Override
    public User update(User entity) {
        return null;
    }

    @Override
    public void delete(Integer id) {

    }

    private User makeUser(ResultSet rs) throws SQLException {
        Integer id = rs.getInt("user_id");
        String login = rs.getString("login");
        String name = rs.getString("name");
        String email = rs.getString("email");
        LocalDate birthday = rs.getDate("birthday").toLocalDate();
        return new User(id, login, name, email, birthday);
    }
}

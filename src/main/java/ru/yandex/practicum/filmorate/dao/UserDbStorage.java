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
import java.util.Optional;

@Repository
public class UserDbStorage implements UserStorage {

    private final JdbcTemplate jdbcTemplate;
    private final Logger log = LoggerFactory.getLogger(UserDbStorage.class);

    @Autowired
    public UserDbStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Optional<User> create(User entity) {
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
        String sql = "SELECT * FROM user";
        return jdbcTemplate.query(sql, (rs, rowNum) -> makeUser(rs));
    }

    @Override
    public Optional<User> getEntity(Integer id) {
        String sql = "SELECT * FROM user WHERE user_id = ?";
        final Optional<User> optionalUser = jdbcTemplate
                .query(sql, (rs, rowNum) -> makeUser(rs), id)
                .stream()
                .findFirst();
        if (optionalUser.isEmpty()) {
            throw new UserNotFoundException(String.format("Пользователь c ID %s не найден", id));
        }
        final User user = optionalUser.get();
        log.info("Найден пользователь: {} {}", user.getId(), user.getName());
        return Optional.of(user);
    }

    @Override
    public Optional<User> update(User entity) {
        String sql = "UPDATE user SET login = ?, name = ?, email = ?, birthday = ? WHERE user_id = ?";
        jdbcTemplate.update(sql,
                entity.getLogin(),
                entity.getName(),
                entity.getEmail(),
                entity.getBirthday(),
                entity.getId());
        User user = new User(
                entity.getId(),
                entity.getLogin(),
                entity.getName(),
                entity.getEmail(),
                entity.getBirthday());
        return Optional.of(user);
    }

    @Override
    public void delete(Integer id) {
        String sql = "DELETE FROM user WHERE user_id = ?";
        jdbcTemplate.update(sql, id);
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

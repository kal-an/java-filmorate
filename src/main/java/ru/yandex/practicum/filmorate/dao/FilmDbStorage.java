package ru.yandex.practicum.filmorate.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.exceptions.UserNotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Optional;

@Repository
public class FilmDbStorage implements FilmStorage {

    private final JdbcTemplate jdbcTemplate;
    private final Logger log = LoggerFactory.getLogger(FilmDbStorage.class);

    @Autowired
    public FilmDbStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Optional<Film> create(Film entity) {

        String sql = "INSERT INTO film (name, description, release_date, duration) VALUES (?, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement stmt = connection.prepareStatement(sql, new String[]{"user_id"});
            stmt.setString(1, entity.getName());
            stmt.setString(2, entity.getDescription());
            stmt.setDate(3, Date.valueOf(entity.getReleaseDate()));
            stmt.setLong(4, entity.getDuration());
            return stmt;
        }, keyHolder);
        return getEntity(keyHolder.getKey().intValue());
    }

    @Override
    public Collection<Film> getEntities() {
        return null;
    }

    @Override
    public Optional<Film> getEntity(Integer id) {
        String sql = "SELECT * FROM film WHERE film_id = ?";
        final Optional<Film> optionalFilm = jdbcTemplate
                .query(sql, (rs, rowNum) -> makeFilm(rs), id)
                .stream()
                .findFirst();
        if (optionalFilm.isEmpty()) {
            throw new UserNotFoundException(String.format("Фильм c ID %s не найден", id));
        }
        final Film film = optionalFilm.get();
        log.info("Найден фильм: {} {}", film.getId(), film.getName());
        return Optional.of(film);
    }

    @Override
    public Optional<Film> update(Film entity) {
        String sql = "UPDATE user SET login = ?, name = ?, email = ?, birthday = ? WHERE user_id = ?";
        jdbcTemplate.update(sql,
                entity.getName(),
                entity.getDescription(),
                entity.getReleaseDate(),
                entity.getDuration(),
                entity.getRate(),
                entity.getId());
        Film film = new Film(
                entity.getName(),
                entity.getDescription(),
                entity.getReleaseDate(),
                entity.getDuration(),
                entity.getRate());
        return Optional.of(film);
    }

    @Override
    public void delete(Integer id) {

    }

    private Film makeFilm(ResultSet rs) throws SQLException {
        Integer id = rs.getInt("film_id");
        String name = rs.getString("name");
        String description = rs.getString("description");
        LocalDate releaseDate = rs.getDate("release_date").toLocalDate();
        Long duration = rs.getLong("duration");
        Integer rate = rs.getInt("rate");
        return new Film(id, name, description, releaseDate, duration, rate);
    }
}

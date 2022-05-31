package ru.yandex.practicum.filmorate.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;

import java.sql.Date;
import java.sql.PreparedStatement;
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
        return Optional.empty();
    }

    @Override
    public Optional<Film> update(Film entity) {
        return Optional.empty();
    }

    @Override
    public void delete(Integer id) {

    }
}

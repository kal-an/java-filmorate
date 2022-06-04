package ru.yandex.practicum.filmorate.storage.film;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.exceptions.UserNotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Mpa;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
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
        String sql = "INSERT INTO film (name, description, release_date, duration, rate, mpa_id) " +
                "VALUES (?, ?, ?, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement stmt = connection.prepareStatement(sql, new String[]{"film_id"});
            stmt.setString(1, entity.getName());
            stmt.setString(2, entity.getDescription());
            stmt.setDate(3, Date.valueOf(entity.getReleaseDate()));
            stmt.setLong(4, entity.getDuration());
            stmt.setLong(5, entity.getRate());
            stmt.setInt(6, entity.getMpa().getId());
            return stmt;
        }, keyHolder);
        return getEntity(keyHolder.getKey().intValue());
    }

    @Override
    public Collection<Film> getEntities() {
        String sql = "SELECT f.film_id, f.name, f.description, f.release_date, f.duration, " +
                "f.rate, m.mpa_id, m.name AS mpa_name, m.description AS mpa_description " +
                "FROM film AS f " +
                "INNER JOIN mpa AS m ON m.mpa_id = f.mpa_id";
        return jdbcTemplate.query(sql, (rs, rowNum) -> makeFilm(rs));
    }

    @Override
    public List<Film> getPopularFilm(Integer size) {
        String sql = "SELECT f.film_id, f.name, f.description, f.release_date, f.duration, " +
                "f.rate, m.mpa_id, m.name AS mpa_name, m.description AS mpa_description " +
                "FROM film AS f " +
                "INNER JOIN mpa AS m ON m.mpa_id = f.mpa_id " +
                "ORDER BY release_date DESC LIMIT ?";
        return jdbcTemplate.query(sql, (rs, rowNum) -> makeFilm(rs), size);
    }

    @Override
    public Optional<Film> getEntity(Integer id) {
        String sql = "SELECT f.film_id, f.name, f.description, f.release_date, f.duration, " +
                "f.rate, m.mpa_id, m.name AS mpa_name, m.description AS mpa_description " +
                "FROM film AS f " +
                "INNER JOIN mpa AS m ON m.mpa_id = f.mpa_id " +
                "WHERE film_id = ?";
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
        String sql = "UPDATE film SET name = ?, description = ?, release_date = ?, duration = ?, rate = ?, mpa_id = ? " +
                "WHERE film_id = ?";
        jdbcTemplate.update(sql,
                entity.getName(),
                entity.getDescription(),
                entity.getReleaseDate(),
                entity.getDuration(),
                entity.getRate(),
                entity.getMpa().getId(),
                entity.getId());
        Film film = new Film(
                entity.getId(),
                entity.getName(),
                entity.getDescription(),
                entity.getReleaseDate(),
                entity.getDuration(),
                entity.getRate(),
                entity.getMpa());
        return Optional.of(film);
    }

    @Override
    public void delete(Integer id) {
        String sql = "DELETE FROM film WHERE film_id = ?";
        jdbcTemplate.update(sql, id);
    }

    private Film makeFilm(ResultSet rs) throws SQLException {
        Integer id = rs.getInt("film_id");
        String name = rs.getString("name");
        String description = rs.getString("description");
        LocalDate releaseDate = rs.getDate("release_date").toLocalDate();
        Long duration = rs.getLong("duration");
        Integer rate = rs.getInt("rate");
        Integer mpa_id = rs.getInt("mpa_id");
        String mpa_name = rs.getString("mpa_name");
        String mpa_description = rs.getString("mpa_description");
        Mpa mpa = new Mpa(mpa_id, mpa_name, mpa_description);
        return new Film(id, name, description, releaseDate, duration, rate, mpa);
    }
}

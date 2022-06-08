package ru.yandex.practicum.filmorate;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import ru.yandex.practicum.filmorate.exceptions.UserNotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.storage.film.FilmDbStorage;

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class FilmDbStorageTests {

    private final FilmDbStorage filmStorage;

    @Test
    @DisplayName("Создать фильм")
    public void testCreateFilm() {
        Mpa mpa = new Mpa(1, "MPA", "MPA Category");
        Film film = new Film(null, "Superman", "Film about Superman",
                LocalDate.now(), 120, 4, mpa, new HashSet<>());
        Optional<Film> createdFilm = filmStorage.create(film);
        assertThat(createdFilm)
                .isPresent()
                .hasValueSatisfying(usr ->
                        assertThat(usr).hasFieldOrPropertyWithValue("id", 4)
                )
                .hasValueSatisfying(usr ->
                        assertThat(usr).hasFieldOrPropertyWithValue("name", film.getName())
                ).hasValueSatisfying(usr ->
                        assertThat(usr).hasFieldOrPropertyWithValue("description", film.getDescription())
                ).hasValueSatisfying(usr ->
                        assertThat(usr).hasFieldOrPropertyWithValue("releaseDate", film.getReleaseDate())
                ).hasValueSatisfying(usr ->
                        assertThat(usr).hasFieldOrPropertyWithValue("duration", film.getDuration())
                ).hasValueSatisfying(usr ->
                        assertThat(usr).hasFieldOrPropertyWithValue("rate", film.getRate())
                );
    }

    @Test
    @DisplayName("Получить все фильмы")
    public void testGetAllFilms() {
        Collection<Film> films = filmStorage.getEntities();
        assertThat(films).hasSize(3);
    }

    @Test
    @DisplayName("Обновить фильм")
    public void testUpdateFilm() {
        Optional<Film> filmOptional = filmStorage.getEntity(1);
        Film film = filmOptional.get();
        film.setRate(8);
        Optional<Film> updatedFilm = filmStorage.update(film);
        assertThat(updatedFilm)
                .isPresent()
                .hasValueSatisfying(usr ->
                        assertThat(usr).hasFieldOrPropertyWithValue("id", updatedFilm.get().getId())
                )
                .hasValueSatisfying(usr ->
                        assertThat(usr).hasFieldOrPropertyWithValue("name", film.getName())
                ).hasValueSatisfying(usr ->
                        assertThat(usr).hasFieldOrPropertyWithValue("description", film.getDescription())
                ).hasValueSatisfying(usr ->
                        assertThat(usr).hasFieldOrPropertyWithValue("releaseDate", film.getReleaseDate())
                ).hasValueSatisfying(usr ->
                        assertThat(usr).hasFieldOrPropertyWithValue("duration", film.getDuration())
                ).hasValueSatisfying(usr ->
                        assertThat(usr).hasFieldOrPropertyWithValue("rate", 8)
                );
    }

    @Test
    @DisplayName("Обновить несуществующий фильм")
    public void testUpdateFilmUnknown() {
        UserNotFoundException exception = assertThrows(UserNotFoundException.class,
                () -> filmStorage.getEntity(-1));
        assertEquals("Фильм c ID -1 не найден", exception.getMessage());
    }

    @Test
    @DisplayName("Получить фильм по корректному ID")
    public void testGetFilmById() {
        Optional<Film> filmOptional = filmStorage.getEntity(1);

        assertThat(filmOptional)
                .isPresent()
                .hasValueSatisfying(film ->
                        assertThat(film).hasFieldOrPropertyWithValue("id", 1)
                );
    }

    @Test
    @DisplayName("Удалить фильм по ID")
    public void testDeleteFilmById() {
        filmStorage.delete(1);
        Collection<Film> films = filmStorage.getEntities();
        assertThat(films).hasSize(2);
    }

    @Test
    @DisplayName("Получить популярные фильмы")
    public void testGetPopularFilms() {
        Collection<Film> films = filmStorage.getPopularFilm(1);
        assertThat(films).hasSize(1);
        assertEquals(films.stream().findFirst().get().getId(), 3);
    }
}

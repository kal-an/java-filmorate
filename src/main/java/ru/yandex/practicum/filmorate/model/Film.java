package ru.yandex.practicum.filmorate.model;

import lombok.*;
import ru.yandex.practicum.filmorate.validators.DateRelease;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.Set;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode(callSuper = true)
public class Film extends Entity {
    @NotNull(message = "Название фильма не может быть пустым")
    @NotBlank(message = "Название фильма не может быть пустым")
    private String name;
    @Size(max = 200, message = "Описание фильма не может быть длиннее 200 символов")
    @NotNull(message = "Описание фильма не может быть пустым")
    @NotBlank(message = "Описание фильма не может быть пустым")
    private String description;
    @NotNull(message = "Дата фильма не может быть пустой")
    @DateRelease(message = "Дата должна быть не раньше 28 декабря 1895 года")
    private LocalDate releaseDate;
    @Min(value = 1, message = "Продолжительность фильма не может быть меньше 0")
    @NotNull(message = "Продолжительность фильма не может быть пустой")
    private long duration;
    @Min(value = 0)
    private int rate;
    private Mpa mpa;
    private Set<Genre> genre;

    public Film(Integer id,
                String name,
                String description,
                LocalDate releaseDate,
                long duration,
                int rate,
                Mpa mpa,
                Set<Genre> genre) {
        this.setId(id);
        this.name = name;
        this.description = description;
        this.releaseDate = releaseDate;
        this.duration = duration;
        this.rate = rate;
        this.mpa = mpa;
        this.genre = genre;
    }
}

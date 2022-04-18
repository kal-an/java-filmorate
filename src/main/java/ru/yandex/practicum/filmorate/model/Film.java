package ru.yandex.practicum.filmorate.model;

import lombok.Builder;
import lombok.Data;
import ru.yandex.practicum.filmorate.validators.DateRelease;

import javax.validation.constraints.*;
import java.time.LocalDate;

@Data
@Builder
public class Film {
    private Integer id;
    @NotNull(message = "Название фильма не может быть пустым")
    @NotBlank(message = "Название фильма не может быть пустым")
    private String name;
    @Size(max = 200, message = "Описание фильма не может быть длиннее 200 символов")
    private String description;
    @NotNull(message = "Дата фильма не может быть пустой")
    @DateRelease(message = "Дата должна быть не раньше 28 декабря 1895 года")
    private LocalDate releaseDate;
    @Min(value = 1, message = "Продолжительность фильма не может быть меньше 0")
    private long duration;
}

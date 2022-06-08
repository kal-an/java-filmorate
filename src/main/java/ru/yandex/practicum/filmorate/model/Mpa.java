package ru.yandex.practicum.filmorate.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class Mpa {
    private Integer id;
    private String name;
    private String description;

    public Mpa(Integer id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }
}

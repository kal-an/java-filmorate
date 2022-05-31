package ru.yandex.practicum.filmorate.model;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;
import java.util.Set;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode(callSuper = true)
public class User extends Entity {
    @NotNull(message = "Логин не может быть пустым.")
    @Pattern(regexp = "^\\S+$", message = "Логин не может содержать пробелов.")
    private String login;
    private String name;
    @NotNull(message = "Адрес электронной почты не может быть пустым.")
    @Pattern(regexp = "^[aA-zZ]+@[a-z]+\\.[a-z]+$",
            message = "Адрес электронной почты некорректен.")
    private String email;
    @NotNull(message = "Дата рождения не может быть пустой.")
    @Past
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthday;
    private Set<Integer> friends;
    private Set<Integer> likedFilms;

    public User(Integer id, String login, String name, String email, LocalDate birthday) {
        this.setId(id);
        this.login = login;
        this.name = name;
        this.email = email;
        this.birthday = birthday;
    }
}

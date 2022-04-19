package ru.yandex.practicum.filmorate.model;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.*;
import java.time.LocalDate;

@Builder
@RequiredArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class User {
    private Integer id;
    @NotNull(message = "Логин не может быть пустым.")
    @NotBlank(message = "Логин не может быть пустым.")
    @Pattern(regexp = "^\\S+$", message = "Логин не может содержать пробелов.")
    private String login;
    @NotNull
    private String name;
    @NotNull(message = "Адрес электронной почты не может быть пустым.")
    @NotBlank(message = "Адрес электронной почты не может быть пустым.")
    @Email(message = "Адрес электронной почты некорректен.")
    @Pattern(regexp = "^[aA-zZ]+@[a-z]+\\.[a-z]+$",
            message = "Адрес электронной почты некорректен.")
    private String email;
    @NotNull(message = "Дата рождения не может быть пустой.")
    @Past
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthday;
}

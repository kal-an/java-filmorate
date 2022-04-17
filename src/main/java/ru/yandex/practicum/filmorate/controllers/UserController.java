package ru.yandex.practicum.filmorate.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exceptions.InvalidUserException;
import ru.yandex.practicum.filmorate.model.GeneratorId;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@RestController
@Slf4j
public class UserController {

    private final Map<Integer, User> users = new HashMap<>();
    public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter
            .ofPattern("yyyy-MM-dd");

    @PostMapping("/users")
    public void create(@RequestBody User user) {
        if (user.getId() != null) {
            log.error(user.toString());
            throw new InvalidUserException("Идентификатор должен быть пустым");
        }
        if (user.getEmail() == null || user.getEmail().isBlank()) {
            log.error(user.toString());
            throw new InvalidUserException("Адрес электронной почты не может быть пустым.");
        }
        if (!user.getEmail().contains("@")) {
            log.error(user.toString());
            throw new InvalidUserException("Адрес электронной почты некорректен.");
        }
        if (user.getLogin() == null || user.getLogin().contains(" ")) {
            log.error(user.toString());
            throw new InvalidUserException("Логин не может быть пустым.");
        }
        if (user.getName() == null || user.getName().isBlank()) {
            log.info(user.toString());
            user.setName(user.getLogin());
        }
        if (LocalDate.parse(user.getBirthday(), DATE_FORMATTER).isAfter(LocalDate.now())) {
            log.error(user.toString());
            throw new InvalidUserException("Некорректная дата рождения.");
        }
        user.setId(GeneratorId.getNewId());
        log.debug(user.toString());
        users.put(user.getId(), user);
    }

    @PutMapping("/users")
    public void update(@RequestBody User user) {
        if (user.getId() == null) {
            log.error(user.toString());
            throw new InvalidUserException("Идентификатор не может быть пустым");
        }
        if (!users.containsKey(user.getId())) {
            log.error(user.toString());
            throw new InvalidUserException("Идентификатор не корректен");
        }
        if (user.getEmail() == null || user.getEmail().isBlank()) {
            log.error(user.toString());
            throw new InvalidUserException("Адрес электронной почты не может быть пустым.");
        }
        if (!user.getEmail().contains("@")) {
            log.error(user.toString());
            throw new InvalidUserException("Адрес электронной почты некорректен.");
        }
        if (user.getLogin() == null || user.getLogin().contains(" ")) {
            log.error(user.toString());
            throw new InvalidUserException("Логин не может быть пустым.");
        }
        if (user.getName() == null || user.getName().isBlank()) {
            log.info(user.toString());
            user.setName(user.getLogin());
        }
        if (LocalDate.parse(user.getBirthday(), DATE_FORMATTER).isAfter(LocalDate.now())) {
            log.error(user.toString());
            throw new InvalidUserException("Некорректная дата рождения.");
        }
        log.debug(user.toString());
        users.put(user.getId(), user);
    }

    @GetMapping("/users")
    public Collection<User> find() {
        log.debug("Текущее количество пользователей: {}", users.size());
        return users.values();
    }
}

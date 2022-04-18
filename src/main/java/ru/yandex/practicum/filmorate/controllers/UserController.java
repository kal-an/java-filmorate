package ru.yandex.practicum.filmorate.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exceptions.InvalidUserException;
import ru.yandex.practicum.filmorate.model.User;

import javax.validation.Valid;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Validated
@RestController
@Slf4j
public class UserController {

    private final Map<Integer, User> users = new HashMap<>();
    private static int id;

    @PostMapping("/users")
    public void create(@Valid @RequestBody User user) {
        if (user.getId() != null) {
            log.error(user.toString());
            throw new InvalidUserException("Идентификатор должен быть пустым");
        }
        if (user.getName() == null || user.getName().isBlank()) {
            log.info(user.toString());
            user.setName(user.getLogin());
        }
        user.setId(++id);
        log.debug(user.toString());
        users.put(id, user);
    }

    @PutMapping("/users")
    public void update(@Valid @RequestBody User user) {
        if (user.getId() == null) {
            log.error(user.toString());
            throw new InvalidUserException("Идентификатор не может быть пустым");
        }
        if (!users.containsKey(user.getId())) {
            log.error(user.toString());
            throw new InvalidUserException("Идентификатор не корректен");
        }
        if (user.getName() == null || user.getName().isBlank()) {
            log.info(user.toString());
            user.setName(user.getLogin());
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

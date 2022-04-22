package ru.yandex.practicum.filmorate.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;

import javax.validation.Valid;
import java.util.Collection;

@Validated
@RestController
@Slf4j
public class UserController extends EntityController<User> {

    @PostMapping("/users")
    @Override
    public void create(@Valid @RequestBody User user) {
        if (user.getName() == null || user.getName().isBlank()) {
            log.info(user.toString());
            user.setName(user.getLogin());
        }
        super.create(user);
    }

    @PutMapping("/users")
    @Override
    public void update(@Valid @RequestBody User user) {
        if (user.getName() == null || user.getName().isBlank()) {
            log.info(user.toString());
            user.setName(user.getLogin());
        }
        super.update(user);
    }

    @GetMapping("/users")
    @Override
    public Collection<User> find() {
        Collection<User> userList = super.find();
        log.debug("Текущее количество пользователей: {}", userList.size());
        return userList;
    }
}

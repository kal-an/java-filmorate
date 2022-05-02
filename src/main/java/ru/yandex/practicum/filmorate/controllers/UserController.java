package ru.yandex.practicum.filmorate.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exceptions.InvalidEntityException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;

import javax.validation.Valid;
import java.util.Collection;

@Validated
@RestController
@Slf4j
public class UserController extends EntityController<User> {

    private final UserService service;

    @Autowired
    public UserController(UserService service) {
        this.service = service;
    }

    @PostMapping("/users")
    @Override
    public void create(@Valid @RequestBody User user) {
        if (user.getName() == null || user.getName().isBlank()) {
            log.info(user.toString());
            user.setName(user.getLogin());
        }
        super.create(user);
        service.addToStorage(user);
    }

    @PutMapping("/users")
    @Override
    public void update(@Valid @RequestBody User user) {
        super.update(user);
        if (user.getName() == null || user.getName().isBlank()) {
            log.info(user.toString());
            user.setName(user.getLogin());
        }
        if (service.findUserById(user.getId()) == null) {
            log.error(user.toString());
            throw new InvalidEntityException("Идентификатор некорректен");
        }
        service.updateInStorage(user);
    }

    @GetMapping("/users")
    public Collection<User> find() {
        return service.getFromStorage();
    }

    @GetMapping("/users/{id}")
    public User getUserById(@PathVariable int id) {
        return service.findUserById(id);
    }
}

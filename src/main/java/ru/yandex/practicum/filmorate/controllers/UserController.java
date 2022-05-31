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
    public User create(@Valid @RequestBody User user) {
        if (user.getName() == null || user.getName().isBlank()) {
            log.info(user.toString());
            user.setName(user.getLogin());
        }
        super.create(user);
        return service.createUser(user);
    }

    @PutMapping("/users")
    @Override
    public User update(@Valid @RequestBody User user) {
        super.update(user);
        if (user.getName() == null || user.getName().isBlank()) {
            log.info(user.toString());
            user.setName(user.getLogin());
        }
        if (service.findUserById(user.getId()) == null) {
            log.error(user.toString());
            throw new InvalidEntityException("Идентификатор некорректен");
        }
        return service.updateUser(user);
    }

    @GetMapping("/users")
    public Collection<User> find() {
        return service.getAllUsers();
    }

    @GetMapping("/users/{id}")
    public User getUserById(@PathVariable int id) {
        return service.findUserById(id);
    }

    @PutMapping("/users/{id}/friends/{friendId}")
    public void addUserToFriends(
            @PathVariable int id,
            @PathVariable int friendId) {
        service.addFriend(id, friendId);
    }

    @DeleteMapping("/users/{id}/friends/{friendId}")
    public void deleteFriend(
            @PathVariable int id,
            @PathVariable int friendId) {
        service.deleteFriend(id, friendId);
    }

    @GetMapping("/users/{id}/friends")
    public Collection<User> getUserFriends(@PathVariable int id) {
        return service.getUserFriends(id);
    }

    @GetMapping("/users/{id}/friends/common/{otherId}")
    public Collection<User> getCommonFriends(
            @PathVariable int id,
            @PathVariable int otherId) {
        return service.getCommonFriends(id, otherId);
    }

}

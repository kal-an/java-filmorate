package ru.yandex.practicum.filmorate.storage.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Component
public class InMemoryUserStorage implements UserStorage {

    private final Map<Integer, User> users = new HashMap<>();

    @Override
    public Optional<User> create(User user) {
        int index = users.size() + 1;
        user.setId(index);
        log.debug(user.toString());
        users.put(index, user);
        return Optional.of(user);
    }

    @Override
    public Collection<User> getEntities() {
        return users.values();
    }

    @Override
    public Optional<User> getEntity(Integer id) {
        return Optional.of(users.get(id));
    }

    @Override
    public Optional<User> update(User user) {
        log.debug(user.toString());
        users.put(user.getId(), user);
        return Optional.of(user);
    }

    @Override
    public void delete(Integer id) {
        users.remove(id);
    }
}

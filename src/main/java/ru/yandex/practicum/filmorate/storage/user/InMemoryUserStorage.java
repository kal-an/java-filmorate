package ru.yandex.practicum.filmorate.storage.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
public class InMemoryUserStorage implements UserStorage {

    private final Map<Integer, User> users = new HashMap<>();

    @Override
    public void create(User entity) {
        int index = users.size() + 1;
        entity.setId(index);
        log.debug(entity.toString());
        users.put(index, entity);
    }

    @Override
    public Collection<User> find() {
        return users.values();
    }

    @Override
    public void update(User entity) {
        log.debug(entity.toString());
        users.put(entity.getId(), entity);
    }

    @Override
    public void delete(Integer id) {
        users.remove(id);
    }
}

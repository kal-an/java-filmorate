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
    public User create(User user) {
        int index = users.size() + 1;
        user.setId(index);
        log.debug(user.toString());
        users.put(index, user);
        return user;
    }

    @Override
    public Collection<User> getEntities() {
        return users.values();
    }

    @Override
    public User getEntity(Integer id) {
        return users.getOrDefault(id, null);
    }

    @Override
    public User update(User user) {
        log.debug(user.toString());
        users.put(user.getId(), user);
        return user;
    }

    @Override
    public void delete(Integer id) {
        users.remove(id);
    }
}

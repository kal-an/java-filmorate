package ru.yandex.practicum.filmorate;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import ru.yandex.practicum.filmorate.exceptions.UserNotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.film.FilmDbStorage;
import ru.yandex.practicum.filmorate.storage.user.UserDbStorage;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class UserDbStorageTests {

    private final UserDbStorage userStorage;

    @Test
    @DisplayName("Создать пользователя")
    public void testCreateUser() {
        User user = new User(null, "jon", "Jon Scott", "jon@mail.com", LocalDate.now());
        Optional<User> createdUser = userStorage.create(user);
        assertThat(createdUser)
                .isPresent()
                .hasValueSatisfying(usr ->
                        assertThat(usr).hasFieldOrPropertyWithValue("id", createdUser.get().getId())
                )
                .hasValueSatisfying(usr ->
                        assertThat(usr).hasFieldOrPropertyWithValue("email", user.getEmail())
                ).hasValueSatisfying(usr ->
                        assertThat(usr).hasFieldOrPropertyWithValue("name", user.getName())
                ).hasValueSatisfying(usr ->
                        assertThat(usr).hasFieldOrPropertyWithValue("login", user.getLogin())
                ).hasValueSatisfying(usr ->
                        assertThat(usr).hasFieldOrPropertyWithValue("birthday", user.getBirthday())
                );
    }

    @Test
    @DisplayName("Получить всех пользователей")
    public void testGetAllUsers() {
        Collection<User> users = userStorage.getEntities();
        assertThat(users).hasSize(3);
    }

    @Test
    @DisplayName("Обновить пользователя")
    public void testUpdateUser() {
        Optional<User> userOptional = userStorage.getEntity(1);
        User user = userOptional.get();
        user.setEmail("jon@mail.com");
        Optional<User> updatedUser = userStorage.update(user);
        assertThat(updatedUser)
                .isPresent()
                .hasValueSatisfying(usr ->
                        assertThat(usr).hasFieldOrPropertyWithValue("id", updatedUser.get().getId())
                )
                .hasValueSatisfying(usr ->
                        assertThat(usr).hasFieldOrPropertyWithValue("email", "jon@mail.com")
                ).hasValueSatisfying(usr ->
                        assertThat(usr).hasFieldOrPropertyWithValue("name", user.getName())
                ).hasValueSatisfying(usr ->
                        assertThat(usr).hasFieldOrPropertyWithValue("login", user.getLogin())
                ).hasValueSatisfying(usr ->
                        assertThat(usr).hasFieldOrPropertyWithValue("birthday", user.getBirthday())
                );
    }

    @Test
    @DisplayName("Обновить несуществующего пользователя")
    public void testUpdateUserUnknown() {
        UserNotFoundException exception = assertThrows(UserNotFoundException.class,
                () -> userStorage.getEntity(-1));
        assertEquals("Пользователь c ID -1 не найден", exception.getMessage());
    }

    @Test
    @DisplayName("Получить пользователя по корректному ID")
    public void testGetUserById() {
        Optional<User> userOptional = userStorage.getEntity(1);

        assertThat(userOptional)
                .isPresent()
                .hasValueSatisfying(user ->
                        assertThat(user).hasFieldOrPropertyWithValue("id", 1)
                );
    }

    @Test
    @DisplayName("Удалить пользователя по ID")
    public void testDeleteUserById() {

        userStorage.delete(1);
        Collection<User> users = userStorage.getEntities();
        assertThat(users).hasSize(2);
    }

    @Test
    @DisplayName("Удалить друга по ID")
    public void testDeleteFriendById() {
        Collection<User> users = userStorage.getUserFriends(1);
        assertThat(users).hasSize(2);
        userStorage.deleteFriend(1, 2);
        users = userStorage.getUserFriends(1);
        assertThat(users).hasSize(1);
    }

    @Test
    @DisplayName("Добавить друга")
    public void testAddFriend() {
        Collection<User> users = userStorage.getUserFriends(2);
        assertThat(users).hasSize(1);
        userStorage.addFriend(2, 1);
        users = userStorage.getUserFriends(2);
        assertThat(users).hasSize(2);
    }

    @Test
    @DisplayName("Удалить like")
    public void testDeleteLike() {
        userStorage.deleteLike(1, 2);
    }

    @Test
    @DisplayName("Добавить like")
    public void testAddLike() {
        userStorage.addLike(2, 2);
    }

    @Test
    @DisplayName("Получить друзей пользователя")
    public void testGetUserFriends() {
        Collection<User> users = userStorage.getUserFriends(1);
        assertThat(users).hasSize(2);
    }

    @Test
    @DisplayName("Получить общих друзей")
    public void testGetCommonFriends() {
        Collection<User> users = userStorage.getCommonFriends(1, 2);
        assertThat(users).hasSize(1);
    }
}

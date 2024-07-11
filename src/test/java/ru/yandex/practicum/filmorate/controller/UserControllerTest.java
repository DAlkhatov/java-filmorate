package ru.yandex.practicum.filmorate.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.exception.ConditionsNotMetException;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class UserControllerTest {

    UserController controller;
    User user;

    @BeforeEach
    void setUp() {
        controller = new UserController();
        user = new User();
        user.setName("name");
        user.setEmail("user@email");
        user.setLogin("login");
        user.setBirthday(LocalDate.of(2000, 1, 1));
    }

    @Test
    @DisplayName("Пользователи должны быть одинаковые")
    void shouldBeEqualUsers() {
        controller.create(user);
        assertEquals(user, controller.findAll().toArray()[0]);
    }

    @Test
    void shouldBeExceptionOfEmptyEmail() {
        user.setEmail("");
        assertThrows(ConditionsNotMetException.class, () -> controller.create(user));
    }

    @Test
    void shouldBeExceptionOfEmail() {
        user.setEmail("email");
        assertThrows(ConditionsNotMetException.class, () -> controller.create(user));
    }

    @Test
    void shouldBeExceptionOfEmptyLogin() {
        user.setLogin("");
        assertThrows(ConditionsNotMetException.class, () -> controller.create(user));
    }

    @Test
    void shouldBeExceptionOfBlinkLogin() {
        user.setLogin("new name");
        assertThrows(ConditionsNotMetException.class, () -> controller.create(user));
    }

    @Test
    void shouldBeEqualsNameAndLogin() {
        user.setName(null);
        controller.create(user);
        assertEquals(user.getName(), user.getLogin());
    }

    @Test
    void shouldBeEqualsLoginAndName() {
        user.setName("");
        controller.create(user);
        assertEquals(user.getName(), user.getLogin());
    }

    @Test
    void shouldBeExceptionOfBirthDay() {
        user.setBirthday(LocalDate.now().plusDays(1));
        assertThrows(ConditionsNotMetException.class, () -> controller.create(user));
    }

    @Test
    void shouldBeExceptionOfId() {
        User newUser = new User();
        assertThrows(ConditionsNotMetException.class, () -> controller.update(newUser));
    }

    @Test
    void shouldBeEqualsName() {
        controller.create(user);
        User newUser = new User();
        newUser.setId(user.getId());
        newUser.setName("");
        newUser.setLogin("");
        controller.update(newUser);
        assertEquals(user.getName(), newUser.getName());
        assertEquals(user.getLogin(), newUser.getLogin());
    }
}
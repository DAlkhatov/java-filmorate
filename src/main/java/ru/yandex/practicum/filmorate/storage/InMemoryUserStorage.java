package ru.yandex.practicum.filmorate.storage;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.ConditionsNotMetException;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Component
public class InMemoryUserStorage implements UserStorage {

    private final Map<Integer, User> users;
    Integer id;

    public InMemoryUserStorage() {
        users = new HashMap<>();
        id = 0;
    }

    @Override
    public Collection<User> getUsers() {
        return users.values();
    }

    @Override
    public User createUser(User user) {
        if (user.getEmail().isEmpty() || !user.getEmail().contains("@")) {
            throw new ConditionsNotMetException("Электронная почта не может быть пустой и должна содержать символ @");
        }
        if (user.getLogin().isEmpty() || user.getLogin().contains(" ")) {
            throw new ConditionsNotMetException("Логин не может быть пустым и содержать пробелы");
        }
        if (user.getName() == null) {
            user.setName(user.getLogin());
        } else if (user.getName().isEmpty()) {
            user.setName(user.getLogin());
        }
        if (user.getBirthday().isAfter(LocalDate.now())) {
            throw new ConditionsNotMetException("Дата рождения не может быть в будущем");
        }
        Integer userid = getNextId();
        user.setId(userid);
        users.put(userid, user);
        log.info("Пользователь {} создан", user);
        return user;
    }

    @Override
    public User deleteUser(User user) {
        if (user.getId() == null) {
            throw new ConditionsNotMetException("Id должен быть указан");
        }
        if (!users.containsKey(user.getId()) || users.get(id) == null) {
            throw new NotFoundException("Фильма с указанным id нет в списке");
        }
        User deletedUser = users.get(user.getId());
        users.remove(deletedUser.getId());
        log.info("Пользователь {} удален", deletedUser);
        return deletedUser;
    }

    @Override
    public User updateUser(User newUser) {
        if (newUser.getId() == null) {
            throw new ConditionsNotMetException("Id должен быть указан");
        }
        User oldUser = users.get(newUser.getId());
        if (newUser.getLogin().isEmpty()) newUser.setLogin(oldUser.getLogin());
        if (newUser.getName().isEmpty()) newUser.setName(oldUser.getName());
        users.put(oldUser.getId(), newUser);
        log.info("Пользователь {} заменен на {}", oldUser, newUser);
        return newUser;
    }

    public Optional<User> getById(int id) {
        return users.values()
                .stream()
                .filter(x -> x.getId() == id)
                .findFirst();
    }

    private int getNextId() {
        return id++;
    }
}

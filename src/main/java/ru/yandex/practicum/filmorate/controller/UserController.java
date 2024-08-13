package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;
import ru.yandex.practicum.filmorate.storage.InMemoryUserStorage;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;

@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {

    private final InMemoryUserStorage userStorage;
    private final UserService service;

    public UserController(InMemoryUserStorage userStorage) {
        this.userStorage = userStorage;
        service = new UserService(userStorage);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Collection<User> findAll() {
        return userStorage.getUsers();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Optional<User> getById(@PathVariable int id) {
        return userStorage.getById(id);
    }

    @GetMapping("/users/{id}/friends")
    @ResponseStatus(HttpStatus.OK)
    public Collection<User> getFriends(@PathVariable int id) {
        return service.getFriends(id);
    }

    @GetMapping("{id}/friends/common/{otherId}")
    @ResponseStatus(HttpStatus.OK)
    public Set<Integer> getMutualFriends(@PathVariable int id, @PathVariable int otherId) {
        return service.getMutualFriends(id, otherId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public User create(@RequestBody User user) {
        return userStorage.createUser(user);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public User update(@RequestBody User newUser) {
        return userStorage.updateUser(newUser);
    }

    @PutMapping("/{id}/friends/{friendId}")
    @ResponseStatus(HttpStatus.OK)
    public void addToFriends(@PathVariable int id, @PathVariable int friendId) {
        service.addToFriend(id, friendId);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.OK)
    public User delete(@RequestBody User user) {
        return userStorage.deleteUser(user);
    }

    @DeleteMapping("/{id}/friends/{friendId}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteFromFriends(@PathVariable int id, @PathVariable int friendId) {
        service.deleteFromFriends(id, friendId);
    }
}

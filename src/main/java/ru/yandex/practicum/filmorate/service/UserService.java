package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.InMemoryUserStorage;

import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
@Slf4j
public class UserService {

    private final InMemoryUserStorage storage;

    public UserService(InMemoryUserStorage storage) {
        this.storage = storage;
    }

    public void addToFriend(int id, int friendId) {
        Optional<User> user = storage.getById(id);
        if (user.isEmpty()) {
            throw new NotFoundException(String.format("user с указанным %d не найден", id));
        }
        user.get().getFriends().add(friendId);
        log.info("Пользователи с id {} и {} теперь друзья", id, friendId);
    }

    public void deleteFromFriends(int id, int friendId) {
        Optional<User> user = storage.getById(id);
        if (user.isEmpty()) {
            throw new NotFoundException("user с указанным id не найден");
        }
        user.get().getFriends().remove(friendId);
        log.info("Пользователи с id {} и {} больше не друзья", id, friendId);
    }

    public Set<Integer> getMutualFriends(int id1, int id2) {
        Optional<User> user1 = storage.getById(id1);
        if (user1.isEmpty()) {
            throw new NotFoundException("user с указанным id1 не найден");
        }
        Optional<User> user2 = storage.getById(id2);
        if (user2.isEmpty()) {
            throw new NotFoundException("user с указанным id2 не найден");
        }
        Set<Integer> mutualFriends = new HashSet<>();
        for (Integer id : user1.get().getFriends()) {
            if (user2.get().getFriends().contains(id)) {
                mutualFriends.add(id);
            }
        }
        log.info("Общий список друзей сформирован");
        return mutualFriends;
    }

    public Collection<User> getFriends(int id) {
        Optional<User> user = storage.getById(id);
        if (user.isEmpty()) {
            throw new NotFoundException("user с указанным id не найден");
        }
        Collection<User> users = null;
        for (Integer i : user.get().getFriends()) {
            Optional<User> u = storage.getById(i);
            if (u.isEmpty()) {
                throw new NullPointerException("один из друзей == null");
            }
            users.add(u.get());
        }
        return users;
    }
}

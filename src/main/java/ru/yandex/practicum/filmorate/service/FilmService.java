package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.InMemoryFilmStorage;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
public class FilmService {

    private final InMemoryFilmStorage filmStorage;

    public FilmService(InMemoryFilmStorage filmStorage) {
        this.filmStorage = filmStorage;
    }

    public void addLike(int filmId, int userId) {
        Optional<Film> film = filmStorage.getById(filmId);
        if (film.isEmpty()) {
            throw new NotFoundException("Заданного фильма нет в хранилище");
        }
        film.get().getLikes().add(userId);
        log.info("Пользователь с id {} поставил лайк фильму {}", userId, film.get());
    }

    public void deleteLike(int filmId, int userId) {
        Optional<Film> film = filmStorage.getById(filmId);
        if (film.isEmpty()) {
            throw new NotFoundException("Заданного фильма нет в хранилище");
        }
        film.get().getLikes().remove(userId);
        log.info("Пользователь с id {} убрал лайк у фильма {}", userId, film.get());
    }

    public Set<Film> getMostLikedFilms(int count) {
        Set<Film> mostLikedFilms;
        mostLikedFilms = filmStorage
                .getFilms()
                .stream()
                .sorted(Film::compareTo)
                .limit(count == 0 ? 10 : count)
                .collect(Collectors.toSet());
        return mostLikedFilms;
    }
}

package ru.yandex.practicum.filmorate.storage;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.ConditionsNotMetException;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Component
public class InMemoryFilmStorage implements FilmStorage {

    Integer id;
    Map<Integer, Film> films;

    public InMemoryFilmStorage () {
        films = new HashMap<>();
        id = 0;
    }

    @Override
    public Collection<Film> getFilms() {
        return films.values();
    }

    @Override
    public Film createFilm(Film film) {
        if (film.getName().isEmpty()) {
            throw new ConditionsNotMetException("Название не может быть пустым");
        }
        if (film.getDescription().length() > 200) {
            throw new ConditionsNotMetException("Максимальная длина описания — 200 символов");
        }
        if (film.getReleaseDate().isBefore(LocalDate.of(1895, 12, 28))) {
            throw new ConditionsNotMetException("Дата релиза — не раньше");
        }
        if (film.getDuration() < 0) {
            throw new ConditionsNotMetException("Продолжительность фильма должна быть положительным числом");
        }
        Integer filmId = getNextId();
        film.setId(filmId);
        films.put(filmId, film);
        log.info("Фильм {} создан", film);
        return film;
    }

    @Override
    public Film deleteFilm(Film film) {
        if (film.getId() == null) {
            throw new ConditionsNotMetException("Id должен быть указан");
        }
        if (!films.containsKey(film.getId()) || films.get(id) == null) {
            throw new NotFoundException("Фильма с указанным id нет в списке");
        }
        Film deletedFilm = films.get(film.getId());
        films.remove(deletedFilm.getId());
        log.info("Фильм {} удален", deletedFilm);
        return deletedFilm;
    }

    @Override
    public Film updateFilm(Film newFilm) {
        if (newFilm.getId() == null) {
            throw new ConditionsNotMetException("Id должен быть указан");
        }
        Film oldFilm = films.get(newFilm.getId());
        if (newFilm.getName().isEmpty()) {
            newFilm.setName(oldFilm.getName());
        }
        films.put(oldFilm.getId(), newFilm);
        log.info("Старый фильм - {} заменен на новый - {}", oldFilm, newFilm);
        return newFilm;
    }

    public Optional<Film> getById(int id) {
        return films.values().stream()
                .filter(x -> x.getId() == id)
                .findFirst();
    }

    private int getNextId() {
        return id++;
    }
}

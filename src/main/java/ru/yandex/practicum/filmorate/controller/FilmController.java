package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;
import ru.yandex.practicum.filmorate.storage.InMemoryFilmStorage;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;

@Slf4j
@RestController
@RequestMapping("/films")
public class FilmController {

    private final InMemoryFilmStorage filmStorage;
    private final FilmService service;

    public FilmController(InMemoryFilmStorage filmStorage) {
        this.filmStorage = filmStorage;
        service = new FilmService(filmStorage);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public Collection<Film> findAll() {
        return filmStorage.getFilms();
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id}")
    public Optional<Film> getById(@PathVariable int id) {
        return filmStorage.getById(id);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/popular")
    public Set<Film> getMostLikedFilms(@RequestParam (required = false, defaultValue = "10") int count) {
        return service.getMostLikedFilms(count);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public Film create(@RequestBody Film film) {
        return filmStorage.createFilm(film);
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping
    public Film update(@RequestBody Film newFilm) {
        return filmStorage.updateFilm(newFilm);
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/{id}/like/{userId}")
    public void addLike(@RequestBody int id, @RequestBody int userId) {
        service.addLike(id, userId);
    }

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping
    public Film delete(@RequestBody Film film) {
        return filmStorage.deleteFilm(film);
    }

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/{id}/like/{userId}")
    public void deleteLike(@RequestBody int id, @RequestBody int userId) {
        service.deleteLike(id, userId);
    }
}

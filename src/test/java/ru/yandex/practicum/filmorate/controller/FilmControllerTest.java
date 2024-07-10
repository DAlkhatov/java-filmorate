package ru.yandex.practicum.filmorate.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.exception.ConditionsNotMetException;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class FilmControllerTest {
    FilmController filmController;
    Film film;

    @BeforeEach
    void setUp() {
        filmController = new FilmController();
        film = new Film();
        film.setName("Film");
        film.setDescription("Description");
        film.setReleaseDate(LocalDate.of(1895, 12, 28));
    }

    @Test
    @DisplayName("Должно выпасть исключение об отсутствии имени")
    void shouldBeExceptionOfTitle() {
        film.setName("");
        assertThrows(ConditionsNotMetException.class, () -> filmController.create(film));
    }

    @Test
    @DisplayName("Должно выпасть исключение о превышении")
    void shouldBeExceptionOfDescription() {
        StringBuilder description = new StringBuilder();
        while (description.length() < 201) {
            description.append("a");
        }
        film.setDescription(String.valueOf(description));
        assertThrows(ConditionsNotMetException.class, () -> filmController.create(film));
    }

    @Test
    @DisplayName("Должно выпасть исключение о дате релиза раньше 28.12.1895")
    void shouldBeExceptionOfDateRelease() {
        film.setReleaseDate(LocalDate.of(1895, 12, 27));
        assertThrows(ConditionsNotMetException.class, () -> filmController.create(film));
    }

    @Test
    @DisplayName("Должно выпасть исключение об отрицательной величине продолжительности фильма")
    void shouldBeExceptionOfDuration() {
        film.setDuration(-1);
        assertThrows(ConditionsNotMetException.class, () -> filmController.create(film));
    }

    @Test
    @DisplayName("Должно выпасть исключение об отсутствии id")
    void shouldBeExceptionOfId() {
        Film newFilm = new Film();
        assertThrows(ConditionsNotMetException.class, () -> filmController.update(newFilm));
    }

    @Test
    @DisplayName("Новый фильм должен взять имя старого фильма")
    void shouldBeEqualsNames() {
        filmController.create(film);
        Film newFilm = new Film();
        newFilm.setId(film.getId());
        newFilm.setName("");
        filmController.update(newFilm);
        assertEquals(film.getName(), newFilm.getName());
    }
}
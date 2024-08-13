package ru.yandex.practicum.filmorate.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDate;
import java.util.Set;

/**
 * Film.
 */
@Data
public class Film implements Comparable<Film> {
    private Integer id;
    @NotBlank
    private String name;
    @Size(max = 200)
    private String description;
    @PastOrPresent
    private LocalDate releaseDate;
    @Positive
    private int duration;
    private Set<Integer> likes;

    @Override
    public int compareTo(Film film) {
        return likes.size() - film.getLikes().size();
    }
}


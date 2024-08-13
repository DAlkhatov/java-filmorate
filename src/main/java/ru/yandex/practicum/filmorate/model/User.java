package ru.yandex.practicum.filmorate.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.LocalDate;
import java.util.Set;

/**
*user
 * */
@Data
public class User {
    private Integer id;
    @Email
    @NotBlank
    private String email;
    @NotBlank
    private String login;
    private String name;
    private LocalDate birthday;
    private Set<Integer> friends;
}

package ru.yandex.practicum.filmorate.controllers;

import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(assignableTypes = {
        FilmController.class,
        UserController.class})
public class ErrorHandler {

}

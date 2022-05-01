package ru.yandex.practicum.filmorate.exceptions;

public class InvalidEntityException extends RuntimeException {

    public InvalidEntityException(String message) {
        super(message);
    }
}

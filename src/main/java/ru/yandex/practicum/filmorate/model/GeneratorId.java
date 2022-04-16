package ru.yandex.practicum.filmorate.model;

public class GeneratorId {

    private static int id;

    public static Integer getNewId() {
        return ++id;
    }
}

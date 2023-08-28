package ru.practicum.exception;

public class WrongTimeException extends RuntimeException {
    public WrongTimeException(final String massage) {
        super(massage);
    }
}

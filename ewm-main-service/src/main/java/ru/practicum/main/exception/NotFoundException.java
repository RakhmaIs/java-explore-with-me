package ru.practicum.main.exception;

public class NotFoundException extends RuntimeException {
    public NotFoundException(final String massage) {
        super(massage);
    }
}

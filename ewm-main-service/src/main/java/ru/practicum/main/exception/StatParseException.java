package ru.practicum.main.exception;

public class StatParseException extends RuntimeException {
    public StatParseException(final String massage) {
        super(massage);
    }
}

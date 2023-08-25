package ru.practicum.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolationException;
import java.time.LocalDateTime;

import static ru.practicum.utill.Constants.FORMATTER;

@Slf4j
@RestControllerAdvice
public class ErrorHandler {

    @ExceptionHandler({MethodArgumentNotValidException.class, MissingRequestHeaderException.class,
            ConstraintViolationException.class, MissingServletRequestParameterException.class,
            WrongTimeException.class})
    public ResponseEntity<ErrorResponse> badRequest(final Exception e) {
        return handleException(e, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> internalServerError(final Exception e) {
        return handleException(e, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private ResponseEntity<ErrorResponse> handleException(final Exception e, HttpStatus status) {
        String errorMessage = status.is4xxClientError() ? "Incorrectly made request." : "Internal Server Error";
        String timestamp = LocalDateTime.now().format(FORMATTER);
        String errorStatus = status.name();
        String errorDescription = e.getMessage();

        log.error("{} - Status: {}, Description: {}, Timestamp: {}",
                errorMessage, errorStatus, errorDescription, timestamp);

        return new ResponseEntity<>(new ErrorResponse(errorStatus, errorDescription, errorMessage, timestamp), status);
    }
}


package ru.practicum.main.exception.handler;

import lombok.Data;

@Data
public class ErrorResponse {

    private String status;
    private String reason;
    private String message;
    private String timestamp;


    public ErrorResponse(String status, String reason, String message, String timestamp) {
        this.status = status;
        this.reason = reason;
        this.message = message;
        this.timestamp = timestamp;
    }
}

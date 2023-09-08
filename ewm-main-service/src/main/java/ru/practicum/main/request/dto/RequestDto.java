package ru.practicum.main.request.dto;

import lombok.*;
import ru.practicum.main.request.Status;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RequestDto {
    private LocalDateTime created;

    private Long event;

    private Long id;

    private Long requester;

    private Status status;
}

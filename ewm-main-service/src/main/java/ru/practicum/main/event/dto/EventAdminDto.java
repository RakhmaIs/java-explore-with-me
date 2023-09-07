package ru.practicum.main.event.dto;

import lombok.*;
import ru.practicum.main.event.status.AdminEventStatus;
import ru.practicum.main.event.location.model.Location;

import java.time.LocalDateTime;



@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EventAdminDto {

    private String annotation;

    private Long category;

    private String description;

    private LocalDateTime eventDate;

    private Location location;

    private Boolean paid;

    private Integer participantLimit;

    private Boolean requestModeration;

    private String title;

    private AdminEventStatus stateAction;
}

package ru.practicum.main.event.dto;

import lombok.*;
import ru.practicum.main.category.dto.CategoryDto;
import ru.practicum.main.event.location.dto.LocationDto;
import ru.practicum.main.event.status.State;
import ru.practicum.main.user.dto.UserDto;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EventFullDto {
    private Long id;

    private String annotation;

    private CategoryDto category;

    private String createdOn;

    private String description;

    private String eventDate;

    private UserDto initiator;

    private LocationDto location;

    private Boolean paid;

    private Integer participantLimit;

    private Boolean requestModeration;

    private State state;

    private String title;

    private Long views;

    private Long confirmedRequests;

    private String publishedOn;
}

package ru.practicum.main.event.dto;

import lombok.*;
import ru.practicum.main.category.model.Category;
import ru.practicum.main.user.model.User;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EventDto {
    private Long id;

    private String annotation;

    private Category category;

    private Long confirmedRequests;

    private LocalDateTime eventDate;

    private User initiator;

    private Boolean paid;

    private String title;

    private Long views;

    private Long commentCount;
}

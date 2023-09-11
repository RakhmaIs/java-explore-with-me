package ru.practicum.main.event.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.main.category.model.Category;
import ru.practicum.main.comment.model.Comment;
import ru.practicum.main.event.location.model.Location;
import ru.practicum.main.event.status.State;
import ru.practicum.main.user.model.User;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EventFullWithComment {
    private Long id;

    private String annotation;

    private Category category;

    private String createdOn;

    private String description;

    private String eventDate;

    private User initiator;

    private Location location;

    private Boolean paid;

    private Integer participantLimit;

    private Boolean requestModeration;

    private State state;

    private String title;

    private Long views;

    private Long confirmedRequests;

    private String publishedOn;

    private List<Comment> comments;
}

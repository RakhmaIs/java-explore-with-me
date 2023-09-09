package ru.practicum.main.event.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EventCommentDto {

    private long id;

    private String title;
}

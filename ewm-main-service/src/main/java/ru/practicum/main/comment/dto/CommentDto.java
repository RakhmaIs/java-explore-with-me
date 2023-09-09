package ru.practicum.main.comment.dto;

import lombok.*;
import ru.practicum.main.event.dto.EventCommentDto;
import ru.practicum.main.user.dto.UserDto;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CommentDto {

    private long id;

    private String text;

    private UserDto author;

    private EventCommentDto event;

    private String createTime;
}

package ru.practicum.main.comment.dto;

import lombok.*;
import ru.practicum.main.user.dto.UserDto;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CommentShortDto {

    private long id;

    private String text;

    private UserDto author;

    private String createTime;
}

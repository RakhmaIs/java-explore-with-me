package ru.practicum.main.comment.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentCountDto {

    private Long eventId;

    private Long commentCount;

    public CommentCountDto(Long eventId, Long commentCount) {
        this.eventId = eventId;
        this.commentCount = commentCount;
    }
}
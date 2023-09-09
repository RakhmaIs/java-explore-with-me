package ru.practicum.main.comment.service;


import ru.practicum.main.comment.dto.CommentDto;
import ru.practicum.main.comment.dto.CommentShortDto;

import java.util.List;

public interface CommentService {

    CommentDto getComment(Long comId);

    List<CommentShortDto> getCommentsByEvent(Long eventId, int from, int size);
}

package ru.practicum.main.comment.service;


import ru.practicum.main.comment.dto.CommentDto;
import ru.practicum.main.comment.dto.CommentCreateDto;
import ru.practicum.main.event.model.Event;

import java.util.Collection;
import java.util.Map;

public interface PrivateCommentService {

    CommentDto createComment(Long userId, Long eventId, CommentCreateDto commentDto);

    void deleteComment(Long userId, Long comId);

    CommentDto patchComment(Long userId, Long comId, CommentCreateDto commentCreateDto);

    Map<Long, Long> getCommentCount(Collection<Event> list);
}

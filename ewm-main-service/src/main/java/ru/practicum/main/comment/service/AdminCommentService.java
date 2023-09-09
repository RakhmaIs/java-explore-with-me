package ru.practicum.main.comment.service;


import ru.practicum.main.comment.dto.CommentDto;

import java.util.List;

public interface AdminCommentService {
    void delete(Long comId);

    List<CommentDto> search(String text);

    List<CommentDto> findAllById(Long userId);
}
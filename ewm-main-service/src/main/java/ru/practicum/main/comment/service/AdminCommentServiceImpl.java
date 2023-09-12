package ru.practicum.main.comment.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.main.comment.dto.CommentDto;
import ru.practicum.main.comment.mapper.CommentMapper;
import ru.practicum.main.comment.model.Comment;
import ru.practicum.main.comment.repository.CommentMainServiceRepository;
import ru.practicum.main.exception.NotFoundException;
import ru.practicum.main.user.repository.UserMainServiceRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class AdminCommentServiceImpl implements AdminCommentService {

    private final CommentMainServiceRepository repository;

    private final UserMainServiceRepository userMainServiceRepository;

    @Override
    public void delete(Long comId) {
        log.info("admin delete - invoked");
        if (repository.existsById(comId)) {
            log.error("User with id = {} not exist", comId);
            throw new NotFoundException("Comment not found");
        }
        log.info("Result: comment with id = {} deleted", comId);
        repository.deleteById(comId);
    }

    @Override
    public List<CommentDto> search(String text) {
        log.info("admin search - invoked");
        List<Comment> list = repository.findAllByText(text);
        log.info("Result: list of comments size = {} ", list.size());
        return CommentMapper.toListCommentDto(list);
    }

    @Override
    public List<CommentDto> findAllById(Long userId) {
        log.info("admin findAllById - invoked");
        if (userMainServiceRepository.existsById(userId)) {
            log.error("User with id = {} not exist", userId);
            throw new NotFoundException("User not found");
        }
        List<Comment> list = repository.findAllByAuthorId(userId);
        log.info("Result: list of comments size = {} ", list.size());
        return CommentMapper.toListCommentDto(list);
    }
}
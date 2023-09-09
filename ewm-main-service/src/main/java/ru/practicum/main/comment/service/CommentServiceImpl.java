package ru.practicum.main.comment.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.main.comment.dto.CommentDto;
import ru.practicum.main.comment.dto.CommentShortDto;
import ru.practicum.main.comment.mapper.CommentMapper;
import ru.practicum.main.comment.model.Comment;
import ru.practicum.main.comment.repository.CommentMainServiceRepository;
import ru.practicum.main.event.repository.EventMainServiceRepository;
import ru.practicum.main.exception.NotFoundException;

import java.util.List;

import static ru.practicum.main.util.Util.createPageRequestAsc;


@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class CommentServiceImpl implements CommentService {

    private final CommentMainServiceRepository repository;

    private final EventMainServiceRepository eventMainServiceRepository;


    @Override
    public CommentDto getComment(Long comId) {
        log.info("getComment - invoked");
        Comment comment = repository.findById(comId)
                .orElseThrow(() -> {
                    log.error("Comment with id = {} - not exist", comId);
                    return new NotFoundException("Comment not found");
                });
        log.info("Result: comment with id= {}", comId);
        return CommentMapper.toCommentDto(comment);
    }

    @Override
    public List<CommentShortDto> getCommentsByEvent(Long eventId, int from, int size) {
        log.info("getCommentsByEvent - invoked");
        if (eventMainServiceRepository.existsById(eventId)) {
            log.error("Event with id = {} - not exist", eventId);
            throw new NotFoundException("Event not found");
        }
        Pageable pageable = createPageRequestAsc("createTime", from, size);
        List<Comment> comments = repository.findAllByEventId(eventId, pageable);
        log.info("Result : list of comments size = {}", comments.size());
        return CommentMapper.toListCommentShortDto(comments);
    }
}

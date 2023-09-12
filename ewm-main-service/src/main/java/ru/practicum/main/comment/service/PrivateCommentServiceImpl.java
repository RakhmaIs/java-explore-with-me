package ru.practicum.main.comment.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.main.comment.dto.CommentCountDto;
import ru.practicum.main.comment.dto.CommentDto;
import ru.practicum.main.comment.dto.CommentCreateDto;
import ru.practicum.main.comment.mapper.CommentMapper;
import ru.practicum.main.comment.model.Comment;
import ru.practicum.main.comment.repository.CommentMainServiceRepository;
import ru.practicum.main.event.model.Event;
import ru.practicum.main.event.repository.EventMainServiceRepository;
import ru.practicum.main.event.status.State;
import ru.practicum.main.exception.ConflictException;
import ru.practicum.main.exception.NotFoundException;
import ru.practicum.main.user.model.User;
import ru.practicum.main.user.repository.UserMainServiceRepository;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class PrivateCommentServiceImpl implements PrivateCommentService {

    private final CommentMainServiceRepository repository;

    private final UserMainServiceRepository userMainServiceRepository;

    private final EventMainServiceRepository eventMainServiceRepository;

    @Transactional
    @Override
    public CommentDto createComment(Long userId, Long eventId, CommentCreateDto commentDto) {
        log.info("createComment - invoked");
        Comment comment = CommentMapper.toComment(commentDto);
        User author = userMainServiceRepository.findById(userId)
                .orElseThrow(() -> {
                    log.error("User with id = {} - not registered", userId);
                    return new NotFoundException("Please register first then you can comment");
                });

        Event event = eventMainServiceRepository.findById(eventId)
                .orElseThrow(() -> {
                    log.error("Event with id = {} - not exist", eventId);
                    return new NotFoundException("Event not found");
                });

        if (!event.getState().equals(State.PUBLISHED)) {
            log.error("Event state = {} - should be PUBLISHED", event.getState());
            throw new ConflictException("Event not published you cant comment it");
        }

        comment.setAuthor(author);
        comment.setEvent(event);
        comment.setCreateTime(LocalDateTime.now().withNano(0));
        log.info("Result: new comment created");
        return CommentMapper.toCommentDto(repository.save(comment));
    }

    @Transactional
    @Override
    public void deleteComment(Long userId, Long comId) {
        log.info("deleteComment - invoked");
        Comment comment = repository.findById(comId)
                .orElseThrow(() -> {
                    log.error("Comment with id = {} - not exist", comId);
                    return new NotFoundException("Comment not found");
                });

        if (!comment.getAuthor().getId().equals(userId)) {
            log.error("Unauthorized access by user");
            throw new ConflictException("you didn't write this comment and can't delete it");
        }
        log.info("Result: comment with id = {} - deleted", comId);
        repository.deleteById(comId);
    }

    @Transactional
    @Override
    public CommentDto patchComment(Long userId, Long comId, CommentCreateDto commentCreateDto) {
        log.info("patchComment - invoked");

        Comment newComment = CommentMapper.toComment(commentCreateDto);

        Comment comment = repository.findById(comId)
                .orElseThrow(() -> {
                    log.error("Comment with id = {} - not exist", comId);
                    return new NotFoundException("Comment not found");
                });

        if (!comment.getAuthor().getId().equals(userId)) {
            log.error("Unauthorized access by user");
            throw new ConflictException("you didn't write this comment and can't patch it");
        }

        comment.setText(newComment.getText());
        comment.setCreateTime(LocalDateTime.now().withNano(0));
        log.info("Result: comment with id = {} - updated", comId);
        return CommentMapper.toCommentDto(comment);
    }

    @Override
    public Map<Long, Long> getCommentCount(Collection<Event> list) {
        List<Long> listEventId = list.stream().map(Event::getId).collect(Collectors.toList());
        List<CommentCountDto> countList = repository.findAllCommentCount(listEventId);
        return countList.stream().collect(Collectors.toMap(CommentCountDto::getEventId, CommentCountDto::getCommentCount));
    }
}

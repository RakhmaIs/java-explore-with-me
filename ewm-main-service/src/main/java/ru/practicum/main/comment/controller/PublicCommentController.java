package ru.practicum.main.comment.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.main.comment.dto.CommentDto;
import ru.practicum.main.comment.dto.CommentShortDto;
import ru.practicum.main.comment.service.CommentService;

import java.util.List;


@RestController
@RequiredArgsConstructor
@Slf4j
public class PublicCommentController {

    private final CommentService service;

    @GetMapping("/comment/{comId}")
    public ResponseEntity<CommentDto> getById(@PathVariable Long comId) {
        log.info("Calling the GET request to /comment/{comId}endpoint");
        return ResponseEntity.ok(service.getComment(comId));
    }

    @GetMapping("/events/{eventId}/comment")
    public ResponseEntity<List<CommentShortDto>> getByEventId(@PathVariable Long eventId,
                                                              @RequestParam(defaultValue = "0") int from,
                                                              @RequestParam(defaultValue = "10") int size) {
        log.info("Calling the GET request to /events/{eventId}/comment");
        return ResponseEntity.ok(service.getCommentsByEvent(eventId, from, size));
    }
}
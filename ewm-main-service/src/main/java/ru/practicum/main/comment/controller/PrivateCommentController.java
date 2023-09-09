package ru.practicum.main.comment.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.main.comment.dto.CommentDto;
import ru.practicum.main.comment.dto.CommentCreateDto;
import ru.practicum.main.comment.service.PrivateCommentService;

@RestController
@Validated
@RequiredArgsConstructor
@Slf4j
public class PrivateCommentController {

    private final PrivateCommentService service;

    @PostMapping("/users/{userId}/events/{eventId}/comment")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<CommentDto> create(@PathVariable Long userId, @PathVariable Long eventId,
                                             @RequestBody @Validated CommentCreateDto commentCreateDto) {
        log.info("Calling the GET request to /users/{userId}/events/{eventId}/comment endpoint");
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(service.createComment(userId, eventId, commentCreateDto));
    }

    @DeleteMapping("/users/{userId}/comment/{comId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<String> delete(@PathVariable Long userId, @PathVariable Long comId) {
        log.info("Calling the GET request to /users/{userId}/comment/{comId} endpoint");
        service.deleteComment(userId, comId);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .body("Comment deleted by user: " + comId);
    }

    @PatchMapping("/users/{userId}/comment/{comId}")
    public ResponseEntity<CommentDto> patch(@PathVariable Long userId, @PathVariable Long comId,
                                            @RequestBody @Validated CommentCreateDto commentCreateDto) {
        log.info("Calling the PATCH request to users/{userId}/comment/{comId} endpoint");
        return ResponseEntity.ok(service.patchComment(userId, comId, commentCreateDto));
    }
}

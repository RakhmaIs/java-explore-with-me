package ru.practicum.main.request.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.main.request.dto.RequestDto;
import ru.practicum.main.request.service.RequestService;

import java.util.List;

@RestController
@Validated
@RequestMapping("/users/")
@RequiredArgsConstructor
@Slf4j
public class RequestController {

    private final RequestService requestService;

    @PostMapping("/{userId}/requests")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<RequestDto> createRequest(@PathVariable Long userId, @RequestParam Long eventId) {
        log.info("Calling the POST request to /users/{userId}/requests endpoint");
        return ResponseEntity.status(HttpStatus.CREATED).body(requestService.createRequest(userId, eventId));
    }

    @GetMapping("/{userId}/requests")
    public ResponseEntity<List<RequestDto>> getRequests(@PathVariable Long userId) {
        log.info("Calling the GET request to /users/{userId}/requests endpoint");
        return ResponseEntity.ok(requestService.readAllRequests(userId));
    }

    @PatchMapping("/{userId}/requests/{requestId}/cancel")
    public ResponseEntity<RequestDto> canselRequest(@PathVariable Long userId, @PathVariable Long requestId) {
        log.info("Calling the PATCH request to /users/{userId}/requests/{requestId}/cancel");
        return ResponseEntity.ok(requestService.cancelRequest(userId, requestId));
    }
}

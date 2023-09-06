package ru.practicum.main.event.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.dto.Validator;
import ru.practicum.main.event.dto.*;
import ru.practicum.main.event.service.EventPrivateService;
import ru.practicum.main.request.dto.RequestDto;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping(path = "/users")
@Slf4j
public class EventPrivateController {

    private final EventPrivateService eventPrivateService;

    @PostMapping("/{userId}/events")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<EventFullDto> createEvent(@PathVariable Long userId, @RequestBody @Validated EventRequestDto eventRequestDto) {
        log.info("Calling the POST request to /users/{userId}/events");
        return ResponseEntity.status(HttpStatus.CREATED).body(eventPrivateService.createEvent(userId, eventRequestDto));
    }

    @GetMapping("/{userId}/events")
    public ResponseEntity<List<EventFullDto>> getEventByUserId(@PathVariable Long userId,
                                                               @RequestParam(defaultValue = "0") @PositiveOrZero int from,
                                                               @RequestParam(defaultValue = "10") @Positive int size) {
        log.info("Calling the GET request to /users/{userId}/events");
        return ResponseEntity.ok(eventPrivateService.readEventByUserId(userId, from, size));
    }

    @PatchMapping("/{userId}/events/{eventId}")
    public ResponseEntity<EventFullDto> patchEvent(@PathVariable Long userId, @PathVariable Long eventId,
                                                   @RequestBody @Validated(Validator.Update.class) UpdateEventDto receivedDto) {
        log.info("Calling the PATCH request to users/{userId}/events/{eventId}");
        return ResponseEntity.ok(eventPrivateService.updateEvent(userId, eventId, receivedDto));
    }

    @GetMapping("/{userId}/events/{eventId}")
    public ResponseEntity<EventFullDto> getEventByUserIdAndEventId(@PathVariable Long userId, @PathVariable Long eventId) {
        log.info("Calling the GET request to users/{userId}/events/{eventId}");
        return ResponseEntity.ok(eventPrivateService.readEventByUserIdAndEventId(userId, eventId));
    }

    @PatchMapping("/{userId}/events/{eventId}/requests")
    public ResponseEntity<RequestShortUpdateDto> patchRequestByOwnerUser(@PathVariable Long userId, @PathVariable Long eventId,
                                                                         @RequestBody RequestShortDto requestShortDto) {
        log.info("Calling the PATCH request to users/{userId}/events/{eventId}/requests");
        return ResponseEntity.ok(eventPrivateService.updateRequestByOwner(userId, eventId, requestShortDto));
    }

    @GetMapping("/{userId}/events/{eventId}/requests")
    public ResponseEntity<List<RequestDto>> getRequestByUserIdAndEventId(@PathVariable Long userId, @PathVariable Long eventId) {
        log.info("Calling the PATCH request to users/{userId}/events/{eventId}");
        return ResponseEntity.ok(eventPrivateService.readRequestByUserIdAndEventId(userId, eventId));
    }
}

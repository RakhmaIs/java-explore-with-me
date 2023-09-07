package ru.practicum.main.event.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.main.event.dto.EventFullDto;
import ru.practicum.main.event.dto.EventShortDto;
import ru.practicum.main.event.service.EventPublicService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.time.LocalDateTime;
import java.util.List;

import static ru.practicum.main.util.Util.TIME_STRING;

@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/events")
@Slf4j
public class EventPublicController {

    private final EventPublicService service;


    @GetMapping
    public ResponseEntity<List<EventShortDto>> findPublicEvents(@RequestParam(required = false) String text,
                                                                @RequestParam(required = false) List<@Positive Long> categories,
                                                                @RequestParam(required = false) Boolean paid,
                                                                @RequestParam(required = false)
                                                                @DateTimeFormat(pattern = TIME_STRING) LocalDateTime rangeStart,
                                                                @RequestParam(required = false)
                                                                @DateTimeFormat(pattern = TIME_STRING) LocalDateTime rangeEnd,
                                                                @RequestParam(defaultValue = "false") Boolean onlyAvailable,
                                                                @RequestParam(required = false) String sort,
                                                                @RequestParam(defaultValue = "0") @PositiveOrZero int from,
                                                                @RequestParam(defaultValue = "10") @Positive int size,
                                                                HttpServletRequest request) {
        log.info("Calling the PATCH request to users/{userId}/events/{eventId}/requests");
        return ResponseEntity.ok(service.readPublicEvents(text, categories, paid,
                rangeStart, rangeEnd, onlyAvailable, sort, from, size, request));
    }


    @GetMapping("/{id}")
    public ResponseEntity<EventFullDto> getPublicEvent(@PathVariable Long id, HttpServletRequest request) {
        log.info("Calling the PATCH request to /events/{id}");
        return ResponseEntity.ok(service.getPublicEvent(id, request));
    }
}

package ru.practicum.main.event.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.dto.Validator;
import ru.practicum.main.event.dto.AdminEventRequestDto;
import ru.practicum.main.event.dto.EventFullDto;
import ru.practicum.main.event.service.EventAdminService;
import ru.practicum.main.event.status.State;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.time.LocalDateTime;
import java.util.List;

import static ru.practicum.main.util.Util.TIME_STRING;

@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/admin/events")
@Slf4j
public class EventAdminController {

    private final EventAdminService eventAdminService;

    @PatchMapping("/{eventId}")
    public ResponseEntity<EventFullDto> patchAdminEvent(@PathVariable Long eventId, @RequestBody @Validated({Validator.Update.class}) AdminEventRequestDto adminEventDto) {
        log.info("Calling the PATCH request to /admin/events/{eventId}");
        return ResponseEntity.ok(eventAdminService.updateAdminEvent(eventId, adminEventDto));
    }

    @GetMapping
    public ResponseEntity<List<EventFullDto>> getAdminEvents(@RequestParam(required = false) List<Long> users,
                                                             @RequestParam(required = false) List<State> states,
                                                             @RequestParam(required = false) List<Long> categories,
                                                             @RequestParam(required = false)
                                                             @DateTimeFormat(pattern = TIME_STRING) LocalDateTime start,
                                                             @RequestParam(required = false)
                                                             @DateTimeFormat(pattern = TIME_STRING) LocalDateTime end,
                                                             @RequestParam(defaultValue = "0") @PositiveOrZero int from,
                                                             @RequestParam(defaultValue = "10") @Positive int size) {
        log.info("Calling the GET request to /admin/events endpoint");
        return ResponseEntity.ok(eventAdminService.readAdminEvents(users, states, categories, start, end, from, size));
    }
}

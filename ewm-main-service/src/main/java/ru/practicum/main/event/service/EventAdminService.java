package ru.practicum.main.event.service;

import ru.practicum.main.event.status.State;
import ru.practicum.main.event.dto.AdminEventRequestDto;
import ru.practicum.main.event.dto.EventFullDto;

import java.time.LocalDateTime;
import java.util.List;

public interface EventAdminService {

    EventFullDto updateAdminEvent(Long eventId, AdminEventRequestDto requestAdminDto);

    List<EventFullDto> readAdminEvents(List<Long> users, List<State> states, List<Long> categories,
                                       LocalDateTime start, LocalDateTime end, int from, int size);
}

package ru.practicum.main.event.service;

import ru.practicum.main.event.dto.*;
import ru.practicum.main.request.dto.RequestDto;

import java.util.List;

public interface EventPrivateService {
    EventFullDto createEvent(Long userId, EventRequestDto eventRequestDto);

    List<EventFullDto> readEventByUserId(Long userId, int from, int size);

    EventFullDto readEventByUserIdAndEventId(Long userId, Long eventId);

    EventFullDto updateEvent(Long userId, Long eventId, UpdateEventDto receivedDto);

    List<RequestDto> readRequestByUserIdAndEventId(Long userId, Long eventId);

    RequestShortUpdateDto updateRequestByOwner(Long userId, Long eventId, RequestShortDto requestShortDto);

}

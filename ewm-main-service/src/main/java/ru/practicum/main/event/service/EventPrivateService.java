package ru.practicum.main.event.service;

import ru.practicum.main.request.dto.RequestDto;
import ru.practicum.main.event.dto.RequestShortDto;
import ru.practicum.main.event.dto.RequestShortUpdateDto;
import ru.practicum.main.event.dto.EventFullDto;
import ru.practicum.main.event.dto.EventRequestDto;
import ru.practicum.main.event.dto.UpdateEventDto;

import java.util.List;

public interface EventPrivateService {
    EventFullDto createEvent(Long userId, EventRequestDto eventRequestDto);

    List<EventFullDto> readEventByUserId(Long userId, int from, int size);

    EventFullDto readEventByUserIdAndEventId(Long userId, Long eventId);

    EventFullDto updateEvent(Long userId, Long eventId,  UpdateEventDto receivedDto);

    List<RequestDto> readRequestByUserIdAndEventId(Long userId, Long eventId);

    RequestShortUpdateDto updateRequestByOwner(Long userId, Long eventId, RequestShortDto requestShortDto);

}

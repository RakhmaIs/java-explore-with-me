package ru.practicum.main.event.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.main.event.status.State;
import ru.practicum.main.event.dto.EventFullDto;
import ru.practicum.main.event.dto.EventShortDto;
import ru.practicum.main.event.repository.EventMainServiceRepository;
import ru.practicum.main.exception.NotFoundException;
import ru.practicum.main.event.mapper.EventMapper;
import ru.practicum.main.event.model.Event;
import ru.practicum.main.event.dto.EventDto;
import ru.practicum.main.stat.service.StatService;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static ru.practicum.main.util.Util.createPageRequestDesc;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class EventPublicServiceImpl implements EventPublicService {

    private final EventMainServiceRepository repository;

    private final StatService statService;


    @Override
    public List<EventShortDto> readPublicEvents(String text, List<Long> categories, Boolean paid, LocalDateTime rangeStart,
                                                LocalDateTime rangeEnd, Boolean onlyAvailable, String sort, Integer from,
                                                Integer size, HttpServletRequest request) {
        log.info("readPublicEvents - invoked");
        sort = (sort != null && sort.equals("EVENT_DATE")) ? "eventDate" : "id";

        List<Event> list = repository.findAllEvents(text, categories, paid, rangeStart, rangeEnd,
                onlyAvailable, sort, createPageRequestDesc(sort, from, size));

        Map<Long, Long> confirmedRequest = statService.toConfirmedRequest(list);
        Map<Long, Long> view = statService.toView(list);

        List<EventDto> events = new ArrayList<>();

        list.forEach(event -> events.add(EventMapper.toEventShort(event, view.getOrDefault(event.getId(), 0L),
                confirmedRequest.getOrDefault(event.getId(), 0L))));

        statService.addHits(request);
        log.info("Result: list event size = {}", events.size());
        return EventMapper.toListEventShortDto(events);
    }

    @Override
    public EventFullDto getPublicEvent(Long id, HttpServletRequest request) {
        log.info("getPublicEvent - invoked");
        Event event = repository.findById(id).orElseThrow(() -> {
            log.error("Event with id = {} not exist", id);
            return new NotFoundException("Event not found");
        });

        if (!event.getState().equals(State.PUBLISHED)) {
            log.error("Event with id = {} not published", id);
            throw new NotFoundException("Event should pe published");
        }

        Map<Long, Long> confirmedRequest = statService.toConfirmedRequest(List.of(event));
        Map<Long, Long> view = statService.toView(List.of(event));

        statService.addHits(request);

        event.setConfirmedRequests(confirmedRequest.getOrDefault(event.getId(), 0L));
        event.setView(view.getOrDefault(event.getId(), 0L));
        log.info("Result: event - {}", event.getTitle());
        return EventMapper.toEventFullDto(event);
    }
}

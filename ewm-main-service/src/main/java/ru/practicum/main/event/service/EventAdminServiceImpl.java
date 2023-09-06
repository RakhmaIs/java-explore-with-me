package ru.practicum.main.event.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.main.event.dto.AdminEventRequestDto;
import ru.practicum.main.event.status.AdminEventStatus;
import ru.practicum.main.event.status.State;
import ru.practicum.main.category.repository.CategoryMainServiceRepository;
import ru.practicum.main.event.dto.EventFullDto;
import ru.practicum.main.event.mapper.EventMapper;
import ru.practicum.main.event.repository.EventMainServiceRepository;
import ru.practicum.main.event.location.repository.LocationMainServiceRepository;
import ru.practicum.main.exception.BadRequestException;
import ru.practicum.main.exception.ConflictException;
import ru.practicum.main.exception.NotFoundException;
import ru.practicum.main.event.dto.EventAdminDto;
import ru.practicum.main.event.model.Event;
import ru.practicum.main.event.location.model.Location;
import ru.practicum.main.stat.service.StatService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static ru.practicum.main.util.Util.createPageRequestAsc;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class EventAdminServiceImpl implements EventAdminService {

    private final EventMainServiceRepository repository;

    private final CategoryMainServiceRepository categoryMainServiceRepository;

    private final LocationMainServiceRepository locationMainServiceRepository;

    private final StatService statService;


    @Transactional
    @Override
    public EventFullDto updateAdminEvent(Long eventId, AdminEventRequestDto adminEventRequestDto) {
        log.info("updateAdminEvent - invoked");
        EventAdminDto eventAdminDto = EventMapper.toAdminEventFromAdminDto(adminEventRequestDto);
        Event event = repository.findById(eventId).orElseThrow(() ->
                new NotFoundException("Event with id = " + eventId + " not found "));

        if (event.getEventDate().isBefore(LocalDateTime.now().plusHours(1))) {
            log.error("Event {} is already in progress or has passed, it cannot be changed", event.getTitle());
            throw new BadRequestException("This event is already in progress or has passed, it cannot be changed");
        }
        if (eventAdminDto.getEventDate() != null) {
            if (eventAdminDto.getEventDate().isBefore(LocalDateTime.now().plusHours(1))) {
                log.error("The data for updating the event date is incorrect. Date: {}", eventAdminDto.getEventDate());
                throw new BadRequestException("The data for updating the event date is incorrect.");
            } else {
                event.setEventDate(eventAdminDto.getEventDate());
            }
        }
        if (eventAdminDto.getStateAction() != null) {
            if (!event.getState().equals(State.PENDING)) {
                log.error("Сan not modify an event that state is {} ", eventAdminDto.getStateAction());
                throw new ConflictException("Сan not modify an event that is not in the status of pending, published, or canceled");
            }

            if (eventAdminDto.getStateAction().equals(AdminEventStatus.PUBLISH_EVENT)) {
                event.setState(State.PUBLISHED);
                event.setPublishedOn(LocalDateTime.now().withNano(0));
            }
            if (eventAdminDto.getStateAction().equals(AdminEventStatus.REJECT_EVENT)) {
                event.setState(State.CANCELED);
            }
        }

        if (eventAdminDto.getRequestModeration() != null) {
            event.setRequestModeration(eventAdminDto.getRequestModeration());
        }
        if (eventAdminDto.getPaid() != null) {
            event.setPaid(eventAdminDto.getPaid());
        }
        if (eventAdminDto.getParticipantLimit() != null) {
            event.setParticipantLimit(eventAdminDto.getParticipantLimit());
        }
        if (eventAdminDto.getLocation() != null) {
            event.setLocation(getLocation(eventAdminDto.getLocation()).orElse(saveLocation(eventAdminDto.getLocation())));
        }
        if (eventAdminDto.getAnnotation() != null && !eventAdminDto.getTitle().isBlank()) {
            event.setAnnotation(eventAdminDto.getAnnotation());
        }
        if (eventAdminDto.getDescription() != null && !eventAdminDto.getDescription().isBlank()) {
            event.setDescription(eventAdminDto.getDescription());
        }
        if (eventAdminDto.getTitle() != null && !eventAdminDto.getTitle().isBlank()) {
            event.setTitle(eventAdminDto.getTitle());
        }
        if (eventAdminDto.getCategory() != null) {
            event.setCategory(categoryMainServiceRepository.findById(eventAdminDto.getCategory())
                    .orElseThrow(() -> new NotFoundException("Категория не найдена")));
        }

        Map<Long, Long> confirmedRequest = statService.toConfirmedRequest(List.of(event));
        Map<Long, Long> view = statService.toView(List.of(event));


        event.setView(view.getOrDefault(eventId, 0L));
        event.setConfirmedRequests(confirmedRequest.getOrDefault(eventId, 0L));
        log.info("Result: updated event {}", event);
        return EventMapper.toEventFullDto(event);
    }


    @Override
    public List<EventFullDto> readAdminEvents(List<Long> users, List<State> states, List<Long> categories, LocalDateTime start, LocalDateTime end, int from, int size) {
        log.info("updateAdminEvent - invoked");

        List<Event> events = repository.findAllByParam(users, states, categories, start, end, createPageRequestAsc(from, size));

        Map<Long, Long> confirmedRequest = statService.toConfirmedRequest(events);
        Map<Long, Long> view = statService.toView(events);

        for (Event event : events) {
            event.setConfirmedRequests(confirmedRequest.getOrDefault(event.getId(), 0L));
            event.setView(view.getOrDefault(event.getId(), 0L));
        }

        log.info("Result: a list of events was returned - size = {}", events.size());
        return EventMapper.toListEventFullDto(events);
    }

    private Location saveLocation(Location location) {
        Location loc = locationMainServiceRepository.save(location);
        log.info("save new location");
        return loc;
    }

    private Optional<Location> getLocation(Location location) {
        Optional<Location> loc = locationMainServiceRepository.findByLatAndLon(location.getLat(), location.getLon());
        log.info("find location");
        return loc;
    }

}
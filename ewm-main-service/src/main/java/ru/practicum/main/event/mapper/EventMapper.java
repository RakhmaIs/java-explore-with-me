package ru.practicum.main.event.mapper;

import lombok.experimental.UtilityClass;
import ru.practicum.main.category.mapper.CategoryMapper;
import ru.practicum.main.category.model.Category;
import ru.practicum.main.event.dto.*;
import ru.practicum.main.event.location.mapper.LocationMapper;
import ru.practicum.main.event.model.Event;
import ru.practicum.main.user.mapper.UserMapper;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@UtilityClass
public class EventMapper {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public Event toEvent(EventRequestDto receivedDto) {
        return Event.builder()
                .eventDate(receivedDto.getEventDate())
                .annotation(receivedDto.getAnnotation())
                .category(Category.builder().id(receivedDto.getCategory()).build())
                .paid(receivedDto.isPaid())
                .description(receivedDto.getDescription())
                .title(receivedDto.getTitle())
                .participantLimit(receivedDto.getParticipantLimit())
                .requestModeration(receivedDto.isRequestModeration())
                .location(LocationMapper.toLocation(receivedDto.getLocation()))
                .build();
    }

    public EventFullDto toEventFullDto(Event event) {
        EventFullDto build = EventFullDto.builder()
                .createdOn(event.getCreatedOn().format(FORMATTER))
                .initiator(UserMapper.toUserDto(event.getInitiator()))
                .confirmedRequests(event.getConfirmedRequests())
                .views(event.getView())
                .state(event.getState())
                .annotation(event.getAnnotation())
                .participantLimit(event.getParticipantLimit())
                .requestModeration(event.getRequestModeration())
                .paid(event.getPaid())
                .title(event.getTitle())
                .id(event.getId())
                .category(CategoryMapper.toCategoryDto(event.getCategory()))
                .description(event.getDescription())
                .eventDate(event.getEventDate().format(FORMATTER))
                .location(LocationMapper.toLocationDto(event.getLocation()))
                .build();

        if (event.getPublishedOn() != null) {
            build.setPublishedOn(event.getPublishedOn().format(FORMATTER));
        }
        return build;
    }

    public List<EventFullDto> toListEventFullDto(List<Event> list) {
        return list.stream().map(EventMapper::toEventFullDto).collect(Collectors.toList());
    }

    public PatchEventDto toEventFromUpdateEvent(UpdateEventDto updateEventDto) {
        PatchEventDto event = PatchEventDto.builder()
                .stateAction(updateEventDto.getStateAction())
                .annotation(updateEventDto.getAnnotation())
                .participantLimit(updateEventDto.getParticipantLimit())
                .requestModeration(updateEventDto.getRequestModeration())
                .category(updateEventDto.getCategory())
                .description(updateEventDto.getDescription())
                .title(updateEventDto.getTitle())
                .paid(updateEventDto.getPaid())
                .eventDate(updateEventDto.getEventDate())
                .build();

        event.setLocation(updateEventDto.getLocation() == null ? null : LocationMapper.toLocation(updateEventDto.getLocation()));

        return event;
    }

    public static EventAdminDto toAdminEventFromAdminDto(AdminEventRequestDto adminEvent) {
        EventAdminDto event = EventAdminDto.builder()
                .stateAction(adminEvent.getStateAction())
                .annotation(adminEvent.getAnnotation())
                .participantLimit(adminEvent.getParticipantLimit())
                .requestModeration(adminEvent.getRequestModeration())
                .category(adminEvent.getCategory())
                .description(adminEvent.getDescription())
                .eventDate(adminEvent.getEventDate())
                .paid(adminEvent.getPaid())
                .title(adminEvent.getTitle())
                .build();

        event.setLocation(adminEvent.getLocation() == null ? null : LocationMapper.toLocation(adminEvent.getLocation()));

        return event;
    }

    public EventDto toEventDto(Event event, Long view, Long confirmedRequests, Long commentCount) {
        return EventDto
                .builder()
                .id(event.getId())
                .eventDate(event.getEventDate())
                .confirmedRequests(confirmedRequests)
                .views(view)
                .annotation(event.getAnnotation())
                .category(event.getCategory())
                .initiator(event.getInitiator())
                .paid(event.getPaid())
                .title(event.getTitle())
                .commentCount(commentCount)
                .build();
    }

    public EventShortDto toEventShortDto(EventDto eventDto) {
        return EventShortDto.builder()
                .initiator(UserMapper.toUserShortDto(eventDto.getInitiator()))
                .views(eventDto.getViews())
                .eventDate(eventDto.getEventDate().format(FORMATTER))
                .annotation(eventDto.getAnnotation())
                .title(eventDto.getTitle())
                .category(CategoryMapper.toCategoryDto(eventDto.getCategory()))
                .confirmedRequests(eventDto.getConfirmedRequests())
                .id(eventDto.getId())
                .paid(eventDto.getPaid())
                .build();
    }

    public static List<EventShortDto> toListEventShortDto(List<EventDto> list) {
        return list.stream().map(EventMapper::toEventShortDto).collect(Collectors.toList());
    }

    public EventDto toEventShort(Event event) {
        return EventDto.builder()
                .id(event.getId())
                .eventDate(event.getEventDate())
                .confirmedRequests(event.getConfirmedRequests())
                .views(event.getView())
                .annotation(event.getAnnotation())
                .category(event.getCategory())
                .initiator(event.getInitiator())
                .paid(event.getPaid())
                .title(event.getTitle())
                .build();
    }

    public static List<EventDto> toListEventShort(List<Event> list) {
        return list.stream().map(EventMapper::toEventShort).collect(Collectors.toList());
    }

    public static EventShortDto toEventShortDto(Event event) {
        return EventShortDto.builder()
                .annotation(event.getAnnotation())
                .category(CategoryMapper.toCategoryDto(event.getCategory()))
                .confirmedRequests(event.getConfirmedRequests())
                .eventDate(event.getEventDate().toString())
                .id(event.getId())
                .initiator(UserMapper.toUserShortDto(event.getInitiator()))
                .paid(event.getPaid())
                .title(event.getTitle())
                .views(event.getView())
                .build();
    }

    public EventCommentDto toEventComment(Event event) {
        return EventCommentDto.builder()
                .id(event.getId())
                .title(event.getTitle())
                .build();
    }
}

package ru.practicum.main.request.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.main.request.Status;
import ru.practicum.main.event.model.Event;
import ru.practicum.main.event.repository.EventMainServiceRepository;
import ru.practicum.main.event.status.State;
import ru.practicum.main.exception.ConflictException;
import ru.practicum.main.exception.NotFoundException;
import ru.practicum.main.request.dto.ConfirmedRequestShortDto;
import ru.practicum.main.request.dto.RequestDto;
import ru.practicum.main.request.mapper.RequestMapper;
import ru.practicum.main.request.model.Request;
import ru.practicum.main.request.repository.RequestMainServiceRepository;
import ru.practicum.main.user.model.User;
import ru.practicum.main.user.repository.UserMainServiceRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class RequestServiceImpl implements RequestService {

    private final RequestMainServiceRepository requestRepository;

    private final UserMainServiceRepository userMainServiceRepository;

    private final EventMainServiceRepository eventMainServiceRepository;


    @Override
    public List<RequestDto> readAllRequests(Long userId) {
        log.info("readAllRequests - invoked");
        List<RequestDto> requests = RequestMapper.toListRequestDto(requestRepository.findAllByRequesterId(userId));
        log.info("Result: list of {} requests", requests.size());
        return requests;
    }

    @Transactional
    @Override
    public RequestDto createRequest(Long userId, Long eventId) {
        log.info("createRequest - invoked");
        Status status;
        Event event = eventMainServiceRepository.findById(eventId).orElseThrow(() -> {
            log.error("Event with id = {} not exist", eventId);
            return new NotFoundException("Event not found");
        });

        if (event.getInitiator().getId().equals(userId)) {
            log.error("Unauthorized access by user");
            throw new ConflictException("The initiator of the event cannot send a request for participation");
        }

        if (!event.getState().equals(State.PUBLISHED)) {
            log.error("Event state = {} - it should be PUBLISHED", event.getState());
            throw new ConflictException("The event must be published in order to participate");
        }

        if (requestRepository.existsByRequesterIdAndEventId(userId, eventId)) {
            log.error("Unauthorized access by user - request already exist");
            throw new ConflictException("You're already participating");
        }

        status = (!event.getRequestModeration() || event.getParticipantLimit() == 0) ? Status.CONFIRMED : Status.PENDING;


        List<ConfirmedRequestShortDto> confirmed = requestRepository.countByEventId(List.of(eventId));

        if (confirmed.size() >= event.getParticipantLimit() && event.getParticipantLimit() != 0) {
            log.error("The list of participants is filled");
            throw new ConflictException("There are no vacancies, try to send a request for another event");
        }
        User user = userMainServiceRepository.findById(userId).orElseThrow(() -> {
            log.error("User with id = {} not exist", userId);
            return new NotFoundException("User not found");
        });

        Request request = requestRepository.save(Request.builder()
                .requester(user)
                .event(event)
                .created(LocalDateTime.now().withNano(0))
                .status(status)
                .build());

        log.info("Result: request {} - created", request);
        return RequestMapper.toRequestDto(request);
    }


    @Transactional
    @Override
    public RequestDto cancelRequest(Long userId, Long requestId) {
        log.info("cancelRequest - invoked");
        if (!userMainServiceRepository.existsById(userId)) {
            log.error("User with id = {} not exist", userId);
            throw new NotFoundException("User not found");
        }
        Request request = requestRepository.findById(requestId).orElseThrow(() -> {
            log.error("Request with id = {} not exist", requestId);
            return new NotFoundException("Request not found");
        });

        request.setStatus(Status.CANCELED);
        log.info("Result:request id = {}, request status = {}", requestId, request.getStatus());
        return RequestMapper.toRequestDto(request);
    }

}

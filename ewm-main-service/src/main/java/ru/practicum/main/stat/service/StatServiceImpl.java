package ru.practicum.main.stat.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.client.StatClient;
import ru.practicum.dto.StatDto;
import ru.practicum.dto.StatResponseDto;
import ru.practicum.main.event.model.Event;
import ru.practicum.main.exception.StatParseException;
import ru.practicum.main.request.dto.ConfirmedRequestShortDto;
import ru.practicum.main.request.repository.RequestMainServiceRepository;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class StatServiceImpl implements StatService {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private final RequestMainServiceRepository requestMainServiceRepository;

    private final StatClient statClient;

    private final ObjectMapper objectMapper;

    @Value("${main_app}")
    private String app;

    @Override
    public Map<Long, Long> toConfirmedRequest(Collection<Event> list) {
        log.info("toConfirmedRequest - invoked");
        List<Long> listEventId = list.stream().map(Event::getId).collect(Collectors.toList());
        List<ConfirmedRequestShortDto> confirmedRequestShortDtoList = requestMainServiceRepository.countByEventId(listEventId);
        Map<Long, Long> confirmedRequest = confirmedRequestShortDtoList.stream()
                .collect(Collectors.toMap(ConfirmedRequestShortDto::getEventId, ConfirmedRequestShortDto::getConfirmedRequestsCount));
        log.info("Result: map of confirmed request size = {}", confirmedRequest.size());
        return confirmedRequest;
    }

    @Override
    public Map<Long, Long> toView(Collection<Event> events) {
        log.info("toView - invoked");
        Map<Long, Long> view = new HashMap<>();
        LocalDateTime start = events.stream().map(Event::getCreatedOn).min(LocalDateTime::compareTo).orElse(null);
        if (start == null) {
            return Map.of();
        }
        List<String> uris = events.stream().map(a -> "/events/" + a.getId()).collect(Collectors.toList());

        ResponseEntity<Object> response = statClient.readStatEvent(start.format(FORMATTER),
                LocalDateTime.now().format(FORMATTER), uris, true);

        try {
            StatResponseDto[] stats = objectMapper.readValue(
                    objectMapper.writeValueAsString(response.getBody()), StatResponseDto[].class);



            for (StatResponseDto stat : stats) {
                view.put(
                        Long.parseLong(stat.getUri().replaceAll("\\D+", "")),
                        stat.getHits());
            }

        } catch (JsonProcessingException e) {
            log.error("JsonProcessingException exc - can't parse");
            throw new StatParseException("Statistics error");
        }
        log.info("Result: view size = {}", view.size());
        return view;
    }

    @Transactional
    @Override
    public void addHits(HttpServletRequest request) {
        statClient.addStatEvent(StatDto.builder()
                .ip(request.getRemoteAddr())
                .timestamp(LocalDateTime.now())
                .uri(request.getRequestURI())
                .app(app)
                .build());
        log.info("add hits");
    }
}

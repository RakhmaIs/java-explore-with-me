package ru.practicum.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.dto.StatDto;
import ru.practicum.dto.StatResponseDto;
import ru.practicum.dto.Validator;
import ru.practicum.service.StatService;

import java.time.LocalDateTime;
import java.util.List;

import static ru.practicum.utill.Constants.DATE_FORMAT;


@RestController
@RequiredArgsConstructor
@Validated
@Slf4j
public class StatServiceController {

    private final StatService statService;

    @PostMapping("/hit")
    public ResponseEntity<StatDto> addStatEvent(@RequestBody @Validated(Validator.Create.class) StatDto statDto) {
        log.info("Calling the POST request to /hit endpoint");
        StatDto statEvent = statService.createStat(statDto);
        return new ResponseEntity<>(statEvent, HttpStatus.CREATED);
    }

    @GetMapping("/stats")
    public ResponseEntity<List<StatResponseDto>> readStatEvent(@RequestParam @DateTimeFormat(pattern = DATE_FORMAT) LocalDateTime start,
                                                               @RequestParam @DateTimeFormat(pattern = DATE_FORMAT) LocalDateTime end,
                                                               @RequestParam(defaultValue = "") List<String> uris,
                                                               @RequestParam(defaultValue = "false") boolean unique) {
        log.info("Calling the GET request to /stats endpoint");
        List<StatResponseDto> stats = statService.readStat(start, end, uris, unique);
        return new ResponseEntity<>(stats, HttpStatus.OK);
    }
}

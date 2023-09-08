package ru.practicum.main.compilation.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.main.compilation.dto.CompilationDto;
import ru.practicum.main.compilation.service.CompilationPublicService;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@RestController
@Validated
@RequestMapping("/compilations")
@RequiredArgsConstructor
@Slf4j
public class CompilationPublicController {

    private final CompilationPublicService service;

    @GetMapping("/{compId}")
    public ResponseEntity<CompilationDto> getCompilationById(@PathVariable Long compId) {
        log.info("Calling the GET request to /compilations/{compId} endpoint");
        CompilationDto response = service.readCompilationById(compId);
        return ResponseEntity.ok(response);


    }

    @GetMapping
    public ResponseEntity<List<CompilationDto>> getCompilation(@RequestParam(required = false) Boolean pinned,
                                                               @RequestParam(defaultValue = "0") @PositiveOrZero int from,
                                                               @RequestParam(defaultValue = "10") @Positive int size) {
        log.info("Calling the GET request to /compilations endpoint");
        List<CompilationDto> list = service.readAllCompilations(pinned, from, size);
        return ResponseEntity.ok(list);
    }
}

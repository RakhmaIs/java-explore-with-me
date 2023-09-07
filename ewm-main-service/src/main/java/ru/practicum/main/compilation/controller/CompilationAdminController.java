package ru.practicum.main.compilation.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.dto.Validator;
import ru.practicum.main.compilation.dto.CompilationDto;
import ru.practicum.main.compilation.dto.NewCompilationDto;
import ru.practicum.main.compilation.service.CompilationAdminService;

@RestController
@Validated
@RequestMapping("/admin/compilations")
@RequiredArgsConstructor
@Slf4j
public class CompilationAdminController {

    private final CompilationAdminService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<CompilationDto> postCompilations(@RequestBody @Validated(Validator.Create.class) NewCompilationDto newCompilationDto) {
        log.info("Calling the POST request to /admin/compilations endpoint");
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(service.createCompilation(newCompilationDto));
    }

    @DeleteMapping("/{compId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<String> deleteCompilation(@PathVariable Long compId) {
        log.info("Calling the DELETE request to /admin/endpoint/{compId}");
        service.deleteCompilation(compId);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .body("Compilation deleted: " + compId);
    }

    @PatchMapping("/{compId}")
    public ResponseEntity<CompilationDto> patchCompilation(@PathVariable Long compId,
                                                           @RequestBody @Validated(Validator.Update.class) NewCompilationDto newCompilationDto) {
        log.info("Calling the PATCH request to /admin/compilations/{compId} endpoint");
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(service.updateCompilation(compId, newCompilationDto));
    }
}

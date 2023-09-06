package ru.practicum.main.category.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.main.category.dto.CategoryDto;
import ru.practicum.main.category.service.CategoryPublicService;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping(path = "/categories")
@Slf4j
public class CategoryPublicController {

    private final CategoryPublicService service;

    @GetMapping
    public ResponseEntity<List<CategoryDto>> readAllCategories(@RequestParam(defaultValue = "0") @PositiveOrZero int from,
                                                               @RequestParam(defaultValue = "10") @Positive int size) {
        log.info("Calling the POST request to - /categories - endpoint");
        return ResponseEntity
                .ok(service.readAllCategories(from, size));
    }

    @GetMapping("/{catId}")
    public ResponseEntity<CategoryDto> readCategoryById(@PathVariable Long catId) {
        log.info("Calling the GET request to - /categories/{catId} - endpoint");
        return ResponseEntity
                .ok(service.readCategoryById(catId));
    }

}

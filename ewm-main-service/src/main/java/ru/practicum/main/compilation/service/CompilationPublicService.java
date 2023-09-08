package ru.practicum.main.compilation.service;

import ru.practicum.main.compilation.dto.CompilationDto;

import java.util.List;

public interface CompilationPublicService {
    CompilationDto readCompilationById(Long compId);

    List<CompilationDto> readAllCompilations(Boolean pinned, int from, int size);
}

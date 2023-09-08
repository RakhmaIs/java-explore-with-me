package ru.practicum.main.compilation.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.main.compilation.dto.CompilationDto;
import ru.practicum.main.compilation.dto.NewCompilationDto;
import ru.practicum.main.compilation.mapper.CompilationMapper;
import ru.practicum.main.compilation.model.Compilation;
import ru.practicum.main.compilation.repository.CompilationMainServiceRepository;
import ru.practicum.main.event.model.Event;
import ru.practicum.main.event.repository.EventMainServiceRepository;
import ru.practicum.main.exception.NotFoundException;
import ru.practicum.main.exception.NotUniqueException;

import java.util.HashSet;
import java.util.Set;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class CompilationAdminServiceImpl implements CompilationAdminService {

    private final CompilationMainServiceRepository compilationMainServiceRepository;

    private final EventMainServiceRepository eventMainServiceRepository;


    @Override
    public CompilationDto createCompilation(NewCompilationDto request) {
        log.info("createCompilation - invoked");

        if (compilationMainServiceRepository.existsByTitle(request.getTitle())) {
            throw new NotUniqueException("Title not unique");
        }

        Set<Event> events;
        events = (request.getEvents() != null && request.getEvents().size() != 0) ?
                new HashSet<>(eventMainServiceRepository.findAllById(request.getEvents())) : new HashSet<>();

        Compilation compilation = Compilation.builder()
                .pinned(request.getPinned() != null && request.getPinned())
                .title(request.getTitle())
                .events(events)
                .build();

        return CompilationMapper.toCompilationsDtoFromCompilation(compilationMainServiceRepository.save(compilation));
    }

    @Override
    public void deleteCompilation(Long compId) {
        log.info("deleteCompilation(- invoked");

        if (!compilationMainServiceRepository.existsById(compId)) {
            throw new NotFoundException("Compilation Not Found");
        }
        log.info("Result: compilation with id {} deleted ", compId);
        compilationMainServiceRepository.deleteById(compId);
    }


    @Override
    public CompilationDto updateCompilation(Long compId, NewCompilationDto newCompilationDto) {
        log.info("updateCompilation - invoked");

        Compilation compilation = compilationMainServiceRepository.findById(compId)
                .orElseThrow(() -> new NotFoundException("Compilation with id " + compId + " not found"));

        if (newCompilationDto.getTitle() != null) {
            compilation.setTitle(newCompilationDto.getTitle());
        }
        if (newCompilationDto.getPinned() != null) {
            compilation.setPinned(newCompilationDto.getPinned());
        }
        if (newCompilationDto.getEvents() != null) {
            HashSet<Event> events = new HashSet<>(eventMainServiceRepository.findAllById(newCompilationDto.getEvents()));
            compilation.setEvents(events);
        }

        Compilation updatedCompilation = compilationMainServiceRepository.save(compilation);

        log.info("Result: compilation with id {} updated ", compId);

        return CompilationMapper.toCompilationsDtoFromCompilation(updatedCompilation);
    }

}

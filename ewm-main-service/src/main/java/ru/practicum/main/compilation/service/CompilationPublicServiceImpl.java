package ru.practicum.main.compilation.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.main.compilation.dto.CompilationDto;
import ru.practicum.main.compilation.mapper.CompilationMapper;
import ru.practicum.main.compilation.model.Compilation;
import ru.practicum.main.compilation.repository.CompilationMainServiceRepository;
import ru.practicum.main.exception.NotFoundException;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class CompilationPublicServiceImpl implements CompilationPublicService {

    private final CompilationMainServiceRepository compilationMainServiceRepository;


    @Override
    public CompilationDto readCompilationById(Long compId) {
        log.info("readCompilationById - invoked");

        Compilation compilation = compilationMainServiceRepository.findById(compId).orElseThrow(() ->
                new NotFoundException("Compilation not found"));

        log.info("Result :  {}", compilation);
        return CompilationMapper.toCompilationDto(compilation);
    }

    @Override
    public List<CompilationDto> readAllCompilations(Boolean pinned, int from, int size) {
        Pageable pageable = PageRequest.of(from, size, Sort.Direction.ASC, "id");

        List<Compilation> compilations;
        compilations = (pinned == null) ? compilationMainServiceRepository.findAll(pageable).getContent() :
                compilationMainServiceRepository.findAllByPinned(pinned, pageable);
        log.info("Result : {}", compilations);
        return CompilationMapper.toCompilationDtoList(compilations);
    }

}

package ru.practicum.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.dto.StatDto;
import ru.practicum.dto.StatResponseDto;
import ru.practicum.exception.WrongTimeException;
import ru.practicum.model.Stat;
import ru.practicum.repository.StatServiceRepository;

import java.time.LocalDateTime;
import java.util.List;

import static ru.practicum.model.mapper.StatMapper.toStat;
import static ru.practicum.model.mapper.StatMapper.toStatDto;

@Service
@RequiredArgsConstructor
@Slf4j
public class StatServiceImpl implements StatService {

    private final StatServiceRepository statServiceRepository;

    @Transactional
    public StatDto createStat(StatDto statDto) {
        log.info("createStat - invoked");
        Stat stat = statServiceRepository.save(toStat(statDto));
        log.info("createStat - stat saved successfully - {}", stat);
        return toStatDto(stat);
    }


    @Override
    @Transactional(readOnly = true)
    public List<StatResponseDto> readStat(LocalDateTime start, LocalDateTime end, List<String> uris, boolean unique) {
        log.info("readStat - invoked");

        if (start.isAfter(end)) {
            log.error("Error occurred: The start date cannot be later than the end date");
            throw new WrongTimeException("The start date cannot be later than the end date");
        }

        if (uris.isEmpty()) {
            if (unique) {
                log.info("readStat - success - unique = true, uris empty");
                return statServiceRepository.findAllByTimestampBetweenStartAndEndWithUniqueIp(start, end);
            } else {
                log.info("readStat - success - unique = false, uris empty");
                return statServiceRepository.findAllByTimestampBetweenStartAndEndWhereIpNotUnique(start, end);
            }
        } else {
            if (unique) {
                log.info("readStat - success - unique = true, uris not empty");
                return statServiceRepository.findAllByTimestampBetweenStartAndEndWithUrisUniqueIp(start, end, uris);
            } else {
                log.info("readStat - success - unique = false, uris not empty");
                return statServiceRepository.findAllByTimestampBetweenStartAndEndWithUrisIpNotUnique(start, end, uris);
            }
        }
    }
}


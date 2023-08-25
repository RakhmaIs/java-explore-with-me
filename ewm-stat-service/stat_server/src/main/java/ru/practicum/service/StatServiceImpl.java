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
        List<StatResponseDto> statOut;
        if (start.isAfter(end)) {
            log.error("Error occurred: The start date cannot be later than the end date");
            throw new WrongTimeException("The start date cannot be later than the end date");
        }
        if (uris.isEmpty()) {
            if (unique) {
                statOut = statServiceRepository.findAllByTimestampBetweenStartAndEndWithUniqueIp(start, end);
                log.info("readStat - success - unique = true, uris empty");
            } else {
                statOut = statServiceRepository.findAllByTimestampBetweenStartAndEndWhereIpNotUnique(start, end);
                log.info("readStat - success - unique = false, uris empty");
            }
        } else {
            if (unique) {
                statOut = statServiceRepository.findAllByTimestampBetweenStartAndEndWithUrisUniqueIp(start, end, uris);
                log.info("readStat - success - unique = true, uris not empty");
            } else {
                statOut = statServiceRepository.findAllByTimestampBetweenStartAndEndWithUrisIpNotUnique(start, end, uris);
                log.info("readStat - success - unique = false, uris not empty");
            }
        }
        return statOut;
    }
}

package ru.practicum.main.request.dto;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import ru.practicum.main.request.Status;

import java.util.List;


@Getter
@Setter
@Builder
@EqualsAndHashCode
public class RequestShortDto {
    private List<Long> requestIds;

    private Status status;
}

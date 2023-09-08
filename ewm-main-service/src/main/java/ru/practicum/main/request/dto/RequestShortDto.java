package ru.practicum.main.request.dto;

import lombok.Builder;
import lombok.Data;
import ru.practicum.main.request.Status;

import java.util.List;


@Data
@Builder
public class RequestShortDto {
    private List<Long> requestIds;

    private Status status;
}

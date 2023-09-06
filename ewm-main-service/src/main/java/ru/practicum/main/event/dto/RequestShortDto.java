package ru.practicum.main.event.dto;

import lombok.*;
import ru.practicum.main.request.Status;

import java.util.List;

@Getter
@Setter
@Builder
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class RequestShortDto {

    private List<Long> requestIds;

    private Status status;
}

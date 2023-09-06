package ru.practicum.main.request.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ConfirmedRequestShortDto {

    private Long eventId;

    private Long confirmedRequestsCount;

    public ConfirmedRequestShortDto(Long eventId, Long confirmedRequestsCount) {
        this.eventId = eventId;
        this.confirmedRequestsCount = confirmedRequestsCount;
    }
}

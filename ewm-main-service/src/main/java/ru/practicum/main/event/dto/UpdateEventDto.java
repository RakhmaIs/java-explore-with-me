package ru.practicum.main.event.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import ru.practicum.dto.Validator;
import ru.practicum.main.event.location.dto.LocationDto;
import ru.practicum.main.event.status.UserEventStatus;

import javax.validation.constraints.Future;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateEventDto {

    @Size(min = 20, max = 2000, groups = Validator.Update.class)
    private String annotation;

    private Long category;

    @Size(min = 20, max = 7000, groups = Validator.Update.class)
    private String description;

    @Future(groups = Validator.Update.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", shape = JsonFormat.Shape.STRING)
    private LocalDateTime eventDate;

    private LocationDto location;

    private Boolean paid;

    @PositiveOrZero(groups = Validator.Update.class)
    private Integer participantLimit;

    private Boolean requestModeration;

    @Size(min = 3, max = 120, groups = Validator.Update.class)
    private String title;

    private UserEventStatus stateAction;
}

package ru.practicum.main.compilation.dto;

import lombok.*;
import ru.practicum.main.event.dto.EventShortDto;

import java.util.List;

@Getter
@Builder
@Setter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class CompilationDto {

    private List<EventShortDto> events;

    private Long id;

    private Boolean pinned;

    private String title;
}

package ru.practicum.main.compilation.dto;

import lombok.*;
import ru.practicum.main.event.dto.EventDto;

import java.util.List;

@Getter
@Setter
@ToString
@Builder
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class CompilationShortDto {

    private List<EventDto> events;

    private Long id;

    private Boolean pinned;

    private String title;


}

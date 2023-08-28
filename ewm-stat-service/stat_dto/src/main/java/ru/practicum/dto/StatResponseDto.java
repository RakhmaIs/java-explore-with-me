package ru.practicum.dto;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
public class StatResponseDto {

    private String app;

    private String uri;

    private long hits;

    public StatResponseDto(String app, String uri, long hits) {
        this.app = app;
        this.uri = uri;
        this.hits = hits;
    }
}

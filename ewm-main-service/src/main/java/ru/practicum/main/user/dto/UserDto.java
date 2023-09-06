package ru.practicum.main.user.dto;

import lombok.*;


@Data
@Builder
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    private Long id;

    private String name;
}

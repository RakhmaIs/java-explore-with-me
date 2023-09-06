package ru.practicum.main.user.dto;

import lombok.*;

@Data
@Builder
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class AdminUserDto {
    private Long id;

    private String name;

    private String email;
}

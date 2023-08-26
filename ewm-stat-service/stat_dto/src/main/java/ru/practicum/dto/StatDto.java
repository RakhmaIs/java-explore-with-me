package ru.practicum.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StatDto {

    @NotBlank(groups = Validator.Create.class)
    @Size(max = 50, groups = Validator.Create.class)
    private String app;

    @NotBlank(groups = Validator.Create.class)
    @Size(max = 50, groups = Validator.Create.class)
    private String uri;

    @NotBlank(groups = Validator.Create.class)
    @Size(max = 15, groups = Validator.Create.class)
    private String ip;

    @NotNull(groups = Validator.Create.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", shape = JsonFormat.Shape.STRING)
    private LocalDateTime timestamp;
}

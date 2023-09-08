package ru.practicum.main.compilation.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.dto.Validator;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NewCompilationDto {

    private Set<Long> events = new HashSet<>();

    private Boolean pinned;

    @NotBlank(groups = {Validator.Create.class})
    @Size(max = 50, groups = {Validator.Create.class, Validator.Update.class})
    private String title;
}

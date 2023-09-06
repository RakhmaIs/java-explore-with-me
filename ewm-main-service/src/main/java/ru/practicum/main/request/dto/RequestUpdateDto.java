package ru.practicum.main.request.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import ru.practicum.main.request.model.Request;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@EqualsAndHashCode
public class RequestUpdateDto {

    private List<Request> conformedRequest = new ArrayList<>();

    private List<Request> canselRequest = new ArrayList<>();
}

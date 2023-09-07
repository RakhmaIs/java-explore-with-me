package ru.practicum.main.request.dto;

import lombok.Data;
import ru.practicum.main.request.model.Request;

import java.util.ArrayList;
import java.util.List;

@Data
public class RequestUpdateDto {

    private List<Request> conformedRequest = new ArrayList<>();

    private List<Request> canselRequest = new ArrayList<>();
}

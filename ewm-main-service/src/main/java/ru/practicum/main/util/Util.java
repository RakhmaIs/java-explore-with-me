package ru.practicum.main.util;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

public class Util {

    private Util() {

    }

    public static PageRequest createPageRequestAsc(int from, int size) {
        return PageRequest.of(from, size, Sort.Direction.ASC, "id");
    }

    public static PageRequest createPageRequestDesc(String sortBy, int from, int size) {
        return PageRequest.of(from > 0 ? from / size : 0, size, Sort.by(sortBy).descending());
    }

}


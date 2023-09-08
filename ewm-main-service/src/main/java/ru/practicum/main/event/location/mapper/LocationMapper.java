package ru.practicum.main.event.location.mapper;

import lombok.experimental.UtilityClass;
import ru.practicum.main.event.location.dto.LocationDto;
import ru.practicum.main.event.location.model.Location;

@UtilityClass
public class LocationMapper {
    public Location toLocation(LocationDto locationDto) {
        return Location.builder()
                .lon(locationDto.getLon())
                .lat(locationDto.getLat())
                .build();
    }

    public LocationDto toLocationDto(Location location) {
        return LocationDto.builder()
                .lat(location.getLat())
                .lon(location.getLon())
                .build();
    }
}

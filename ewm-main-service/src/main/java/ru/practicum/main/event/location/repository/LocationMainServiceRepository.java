package ru.practicum.main.event.location.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.main.event.location.model.Location;

import java.util.Optional;

public interface LocationMainServiceRepository extends JpaRepository<Location, Long> {
    Optional<Location> findByLatAndLon(double lat, double lon);
}

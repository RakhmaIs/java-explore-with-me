package ru.practicum.main.user.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.main.user.model.User;

import java.util.List;

public interface UserMainServiceRepository extends JpaRepository<User, Long> {

    @Query("SELECT u " +
            "FROM User u " +
            "WHERE u.id IN ?1 ")
    List<User> findAllById(List<Long> id, Pageable pageable);

    @Query("SELECT u " +
            "FROM User u")
    List<User> findAllUser(Pageable pageable);

    Boolean existsByEmail(String email);
}

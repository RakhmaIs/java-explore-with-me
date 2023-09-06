package ru.practicum.main.category.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.main.category.model.Category;

import java.util.List;

public interface CategoryMainServiceRepository extends JpaRepository<Category, Long> {

    boolean existsByName(String name);

    @Query("SELECT c " +
            "FROM Category c ")
    List<Category> findAllCategories(Pageable pageable);
}

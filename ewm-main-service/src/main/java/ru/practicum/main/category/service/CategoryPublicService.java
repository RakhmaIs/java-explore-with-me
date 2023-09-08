package ru.practicum.main.category.service;

import ru.practicum.main.category.dto.CategoryDto;

import java.util.List;

public interface CategoryPublicService {
    List<CategoryDto> readAllCategories(Integer from, Integer size);

    CategoryDto readCategoryById(Long catId);
}

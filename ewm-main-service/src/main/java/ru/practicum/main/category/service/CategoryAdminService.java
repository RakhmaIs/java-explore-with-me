package ru.practicum.main.category.service;

import ru.practicum.main.category.dto.CategoryDto;

public interface CategoryAdminService {
    CategoryDto createCategory(CategoryDto categoryDto);

    void deleteCategory(Long catId);

    CategoryDto updateCategory(Long catId, CategoryDto categoryDto);

}

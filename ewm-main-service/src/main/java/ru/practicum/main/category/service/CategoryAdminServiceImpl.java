package ru.practicum.main.category.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.main.category.dto.CategoryDto;
import ru.practicum.main.category.mapper.CategoryMapper;
import ru.practicum.main.category.model.Category;
import ru.practicum.main.category.repository.CategoryMainServiceRepository;
import ru.practicum.main.event.repository.EventMainServiceRepository;
import ru.practicum.main.exception.ConflictException;
import ru.practicum.main.exception.NotFoundException;
import ru.practicum.main.exception.NotUniqueException;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class CategoryAdminServiceImpl implements CategoryAdminService {

    private final CategoryMainServiceRepository categoryMainServiceRepository;

    private final EventMainServiceRepository eventMainServiceRepository;

    @Override
    @Transactional
    public CategoryDto createCategory(CategoryDto requestCategory) {
        log.info("createCategories - invoked");
        if (categoryMainServiceRepository.existsByName(requestCategory.getName())) {
            log.error("Category name not unique {}", requestCategory.getName());
            throw new NotUniqueException("Category with this name already exists");
        }
        try {
            Category result = categoryMainServiceRepository.saveAndFlush(CategoryMapper.toCategories(requestCategory));
            log.info("Result: category - {} - saved", result.getName());
            return CategoryMapper.toCategoryDto(result);

        } catch (DataIntegrityViolationException e) {
            log.error("Category already exist = {}", requestCategory);
            throw new ConflictException("Category with name " + requestCategory.getName() + " already exits");
        }
    }

    @Override
    @Transactional
    public void deleteCategory(Long catId) {
        log.info("deleteCategories - invoked");

        if (!categoryMainServiceRepository.existsById(catId)) {
            log.error("Category with this id does not exist {}", catId);
            throw new NotFoundException("Category with this id does not exist");
        }

        if (eventMainServiceRepository.existsByCategoryId(catId)) {
            throw new ConflictException("Can't delete a category with associated events");
        }

        if (!categoryMainServiceRepository.existsById(catId)) {
            throw new NotFoundException("Category does not exist");
        }
        log.info("Result: category with id - {} - deleted", catId);
        categoryMainServiceRepository.deleteById(catId);
    }

    @Transactional
    @Override
    public CategoryDto updateCategory(Long catId, CategoryDto categoryDto) {
        log.info("updateCategories - invoked");
        Category category = categoryMainServiceRepository.findById(catId).orElseThrow(()
                -> new NotFoundException("This Category not found"));

        if (!category.getName().equals(categoryDto.getName()) &&
                categoryMainServiceRepository.existsByName(categoryDto.getName())) {
            log.error("Category with this name not unique: {}", categoryDto.getName());
            throw new NotUniqueException("Category with this name not unique: " + categoryDto.getName());
        }
        category.setName(categoryDto.getName());
        log.info("Result: category - {} updated", category.getName());
        return CategoryMapper.toCategoryDto(category);
    }

}

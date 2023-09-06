package ru.practicum.main.category.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.main.category.dto.CategoryDto;
import ru.practicum.main.category.mapper.CategoryMapper;
import ru.practicum.main.category.model.Category;
import ru.practicum.main.category.repository.CategoryMainServiceRepository;
import ru.practicum.main.exception.NotFoundException;

import java.util.List;

import static ru.practicum.main.util.Util.createPageRequestAsc;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class CategoryPublicServiceImpl implements CategoryPublicService {

    private final CategoryMainServiceRepository repository;

    @Override
    public List<CategoryDto> readAllCategories(Integer from, Integer size) {
        log.info("readAllCategories - invoked");
        List<Category> cat = repository.findAllCategories(createPageRequestAsc(from, size));
        log.info("Result: categories size = {}", cat.size());
        return CategoryMapper.toListCategoriesDto(cat);

    }

    @Override
    public CategoryDto readCategoryById(Long catId) {
        log.info("readAllCategories - invoked");
        Category category = repository.findById(catId).orElseThrow(() -> {
            log.error("Category with id = {} not exist", catId);
            return new NotFoundException("Category not found");
        });
        log.info("Result: received a category - {}", category.getName());
        return CategoryMapper.toCategoryDto(category);
    }
}

package de.ExpenseTracker.service;

import de.ExpenseTracker.dto.CategoryData;
import de.ExpenseTracker.exceptions.CategoryNotFoundException;
import de.ExpenseTracker.exceptions.ErrorCode;
import de.ExpenseTracker.model.Category;
import de.ExpenseTracker.repository.CategoryRepository;
import lombok.AllArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CategoryService {
    private CategoryRepository categoryRepository;
    private final MessageSource messageSource;

    public List<CategoryData> getMainCategories() {
        Locale locale = getLocale();

        List<Category> mainCategories = categoryRepository.findByParentIsNull();

        return mainCategories
                .stream()
                .map(category -> CategoryData.builder()
                        .categoryId(category.getCategoryId())
                        .localizedName(messageSource.getMessage(category.getName(), null, locale))
                        .build())
                .collect(Collectors.toList());
    }

    public List<CategoryData> getSubCategories(UUID categoryId) {
        Locale locale = getLocale();

        List<Category> subCategories = categoryRepository.findByParentCategoryId(categoryId);

        return subCategories
                .stream()
                .map(subCategory -> CategoryData.builder()
                        .categoryId(subCategory.getCategoryId())
                        .localizedName(messageSource.getMessage(subCategory.getName(), null, locale))
                        .build())
                .collect(Collectors.toList());
    }

    private Locale getLocale() {
        return LocaleContextHolder.getLocale();
    }

    /**
     * Retrieves a Category entity from the database by its UUID
     *
     * @param categoryId the ID of the category to fetch
     * @return the Category entity
     * @throws CategoryNotFoundException if no category with the given ID exists
     */
    public Category getCategoryByIdOrThrow(UUID categoryId) {
        return categoryRepository.findById(categoryId)
                .orElseThrow(() -> new CategoryNotFoundException(ErrorCode.CATEGORY_NOT_FOUND));
    }
}
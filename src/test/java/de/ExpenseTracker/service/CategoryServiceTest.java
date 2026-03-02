package de.ExpenseTracker.service;

import de.ExpenseTracker.dto.CategoryData;
import de.ExpenseTracker.model.Category;
import de.ExpenseTracker.repository.CategoryRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CategoryServiceTest {
    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private CategoryService categoryService;

    @Mock
    private MessageSource messageSource;

    @Test
    void shouldReturnMainCategoriesTranslated() {
        Locale locale = LocaleContextHolder.getLocale();
        Category main1 = createMainCategory("main one");
        Category main2 = createMainCategory("main two");

        List<Category> mainCategories = List.of(main1, main2);

        when(categoryRepository.findByParentIsNull()).thenReturn(mainCategories);
        when(messageSource.getMessage("main one", null, locale)).thenReturn("Main eins");
        when(messageSource.getMessage("main two", null, locale)).thenReturn("Main zwei");

        List<CategoryData> result = categoryService.getMainCategories();

        assertEquals("Main eins", result.get(0).getLocalizedName());
        assertEquals("Main zwei", result.get(1).getLocalizedName());

        verify(categoryRepository, times(1)).findByParentIsNull();
        verify(messageSource, times(1)).getMessage("main one", null, locale);
        verify(messageSource, times(1)).getMessage("main two", null, locale);
    }

    @Test
    void shouldReturnSubCategoriesTranslated() {
        Locale locale = LocaleContextHolder.getLocale();

        Category main1 = createMainCategory("main one");
        Category child1 = createChildCategory("child one", main1);
        Category child2 = createChildCategory("child two", main1);

        List<Category> childCategories = List.of(child1, child2);

        when(categoryRepository.findByParentCategoryId(main1.getCategoryId())).thenReturn(childCategories);
        when(messageSource.getMessage("child one", null, locale)).thenReturn("Child eins");
        when(messageSource.getMessage("child two", null, locale)).thenReturn("Child zwei");

        List<CategoryData> result = categoryService.getSubCategories(main1.getCategoryId());

        assertEquals("Child eins", result.get(0).getLocalizedName());
        assertEquals("Child zwei", result.get(1).getLocalizedName());
    }

    // Edge Test to check if the program returns an empty list instead of NullPointerException
    @Test
    void returnsEmptyListWhenNoValidMainCategoriesExist() {
        when(categoryRepository.findByParentIsNull()).thenReturn(Collections.emptyList());

        List<CategoryData> result = categoryService.getMainCategories();
        assertTrue(result.isEmpty());
    }

    // This edge case tests a request for a category’s children when the category has no children.
    @Test
    void shouldReturnEmptyListForSingleCategories() {
        Category main1 = createMainCategory("main one");
        when(categoryRepository.findByParentCategoryId(main1.getCategoryId())).thenReturn(Collections.emptyList());

        List<CategoryData> result = categoryService.getSubCategories(main1.getCategoryId());

        assertTrue(result.isEmpty());
    }

    // helper method to create Categories
    private Category createMainCategory(String name) {
        return Category.builder()
                .categoryId(UUID.randomUUID())
                .name(name)
                .parent(null)
                .build();
    }

    private Category createChildCategory(String name, Category parent) {
        return Category.builder()
                .categoryId(UUID.randomUUID())
                .name(name)
                .parent(parent)
                .build();
    }
}
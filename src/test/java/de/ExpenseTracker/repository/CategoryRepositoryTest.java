package de.ExpenseTracker.repository;

import de.ExpenseTracker.model.Category;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
public class CategoryRepositoryTest {

    @Autowired
    private CategoryRepository categoryRepository;

    @BeforeEach
    void setUp() {
        categoryRepository.deleteAll();
    }

    @Test
    void findAll_returnsOnlyMainAndSingleCategories() {
        Category parent = categoryRepository.save(createMainCategory());
        categoryRepository.save(createChildCategory(parent));

        List<Category> categories = categoryRepository.findByParentIsNull();

        assertEquals(1, categories.size());
    }

    @Test
    void findAll_returnsOnlyChildrenByParentId() {
        Category parent = categoryRepository.save(createMainCategory());
        categoryRepository.save(createChildCategory(parent));

        List<Category> categories = categoryRepository.findByParent_CategoryId(parent.getCategoryId());
        assertEquals(1, categories.size());
    }

    // helper methods for creating categories
    private Category createMainCategory() {
        return Category.builder()
                .categoryId(UUID.randomUUID())
                .name("testMain")
                .parent(null)
                .build();
    }

    private Category createChildCategory(Category parent) {
        return Category.builder()
                .categoryId(UUID.randomUUID())
                .name("testChild")
                .parent(parent)
                .build();
    }
}
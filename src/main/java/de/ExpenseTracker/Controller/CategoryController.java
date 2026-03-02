package de.ExpenseTracker.Controller;

import de.ExpenseTracker.dto.CategoryData;
import de.ExpenseTracker.service.CategoryService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/categories")
@AllArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    /**
     * Retrieves all main categories
     *
     * @return List of category DTOs of main categories
     */
    @GetMapping("/main")
    public List<CategoryData> getMainCategories() {
        return categoryService.getMainCategories();
    }

    /**
     * Retrieves all sub categories
     *
     * @param parentId UUID of the main category
     * @return List of category DTOs that are the children of a main category
     */
    @GetMapping("/{parentId}/subcategories")
    public List<CategoryData> getSubCategories(@PathVariable UUID parentId) {
        return categoryService.getSubCategories(parentId);
    }
}
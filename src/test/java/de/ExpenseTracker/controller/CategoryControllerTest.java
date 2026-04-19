package de.ExpenseTracker.controller;

import de.ExpenseTracker.Controller.CategoryController;
import de.ExpenseTracker.dto.CategoryData;
import de.ExpenseTracker.service.CategoryService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CategoryController.class)
@AutoConfigureMockMvc(addFilters = false)
public class CategoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private CategoryService categoryService;

    // main categories
    @Test
    void testShouldReturnMainCategories() throws Exception {
        List<CategoryData> categoryDataList = List.of(createMainCategory());

        when(categoryService.getMainCategories()).thenReturn(categoryDataList);

        mockMvc.perform(get("/categories/main"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].localizedName").value("CategoryMain"))
                .andExpect(jsonPath("$[0].categoryId").value(categoryDataList.getFirst().getCategoryId().toString()));
    }

    // sub categories
    // positive
    @Test
    void testShouldReturnSubCategories() throws Exception {
        UUID parent = UUID.randomUUID();
        List<CategoryData> categoryDataList = List.of(createMainCategory());

        when(categoryService.getSubCategories(parent)).thenReturn(categoryDataList);

        mockMvc.perform(get("/categories/{parent}/sub", parent))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].categoryId").value(categoryDataList.getFirst().getCategoryId().toString()))
                .andExpect(jsonPath("$[0].localizedName").value("CategoryMain"));
    }

    // negative
    @Test
    void testShouldReturnEmptySubCategories() throws Exception {
        UUID parent = UUID.randomUUID();

        when (categoryService.getSubCategories(parent)).thenReturn(List.of());

        mockMvc.perform(get("/categories/{parent}/sub", parent))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isEmpty());
    }

    // helper method for creating categories
    private CategoryData createMainCategory() {
        return CategoryData.builder()
                .categoryId(UUID.randomUUID())
                .localizedName("CategoryMain")
                .build();
    }
}
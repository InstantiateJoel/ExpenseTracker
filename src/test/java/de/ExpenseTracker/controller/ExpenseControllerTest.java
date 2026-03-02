package de.ExpenseTracker.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.ExpenseTracker.Controller.ExpenseController;
import de.ExpenseTracker.dto.ExpenseData;
import de.ExpenseTracker.exceptions.ErrorCode;
import de.ExpenseTracker.exceptions.UserNotFoundException;
import de.ExpenseTracker.service.ExpenseService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.MessageSource;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ExpenseController.class)
public class ExpenseControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private ExpenseService expenseService;

    @MockitoBean(name = "messageSource")
    private MessageSource messageSource;

    // create expense
    // positive
    @Test
    @WithMockUser(username = "joel")
    void testShouldReturnSuccessWhenCreatingExpense() throws Exception {
        ExpenseData expenseData = createExpenseData();

        when(expenseService.createNewExpense(any(ExpenseData.class)))
                .thenReturn(expenseData);

        mockMvc.perform(post("/expense")
                        .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(expenseData)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.category").value(expenseData.getCategory().toString()))
                .andExpect(jsonPath("$.amount").value(expenseData.getAmount()))
                .andExpect(jsonPath("$.description").value(expenseData.getDescription()));
    }

    // negative
    @Test
    @WithMockUser(username = "joel")
    void testShouldReturnBadRequestWhenUserNotFound() throws Exception {
        Mockito.doThrow(new UserNotFoundException(ErrorCode.USER_NOT_FOUND))
                .when(expenseService)
                .createNewExpense(any(ExpenseData.class));

        Mockito.when(messageSource.getMessage(
                eq(ErrorCode.USER_NOT_FOUND.getCode()),
                isNull(),
                any(Locale.class)
        )).thenReturn("Username not found!");


        mockMvc.perform(post("/expense")
                        .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createExpenseData())))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.messageKey").value("Username not found!"));
    }

    // get user expenses
    @Test
    @WithMockUser(username = "joel")
    void testShouldReturnUserExpenses() throws Exception {
        ExpenseData expenseData = createExpenseData();

        when(expenseService.getExpensesForCurrentUser())
                .thenReturn(List.of(expenseData));

        mockMvc.perform(get("/expense")
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].category").value(expenseData.getCategory().toString()))
                .andExpect(jsonPath("$[0].amount").value(12.4))
                .andExpect(jsonPath("$[0].description").value("description"));
    }

    // helper methods
    private ExpenseData createExpenseData() {
        return ExpenseData.builder()
                .category(UUID.randomUUID())
                .amount(BigDecimal.valueOf(12.40))
                .description("description")
                .build();
    }
}

package de.ExpenseTracker.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.ExpenseTracker.Controller.IncomeController;
import de.ExpenseTracker.dto.IncomeData;
import de.ExpenseTracker.service.IncomeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static de.ExpenseTracker.TestDataFactory.createIncomeData;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(IncomeController.class)
public class IncomeControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private IncomeService incomeService;

    // positive
    @Test
    @WithMockUser(username = "joel")
    void testShouldReturnSuccessWhenCreatingIncome() throws Exception {
        IncomeData incomeData = createIncomeData();

        when(incomeService.createNewIncome(any(IncomeData.class)))
                .thenReturn(incomeData);

        mockMvc.perform(post("/income")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createIncomeData())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value(incomeData.getTitle()))
                .andExpect(jsonPath("$.amount").value(incomeData.getAmount()))
                .andExpect(jsonPath("$.incomeDate").value(incomeData.getIncomeDate().toString()));
    }
}
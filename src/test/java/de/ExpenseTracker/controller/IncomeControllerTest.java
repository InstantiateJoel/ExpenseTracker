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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static de.ExpenseTracker.TestDataFactory.createIncomeData;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@WebMvcTest(IncomeController.class)
public class IncomeControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private IncomeService incomeService;

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

    @Test
    @WithMockUser(username = "joel")
    void testShouldReturnBadRequestWhenTitleMissing() throws Exception {
        IncomeData incomeData = IncomeData.builder()
                .amount(BigDecimal.TEN)
                .incomeDate(LocalDate.now())
                .build();

        mockMvc.perform(post("/income")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(incomeData)))
                .andExpectAll(status().isBadRequest());
    }

    @Test
    @WithMockUser(username = "joel")
    void testShouldReturnUserIncomes() throws Exception {
        IncomeData incomeData = createIncomeData();

        when(incomeService.getIncomesForCurrentUser())
                .thenReturn(List.of(incomeData));

        mockMvc.perform(get("/income")
                .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value(incomeData.getTitle()))
                .andExpect(jsonPath("$[0].amount").value(incomeData.getAmount()))
                .andExpect(jsonPath("$[0].incomeDate").value(incomeData.getIncomeDate().toString()));
    }

    @Test
    @WithMockUser(username = "joel")
    void testShouldDeleteIncome() throws Exception {
        UUID incomeId = UUID.randomUUID();

        doNothing().when(incomeService).deleteIncome(incomeId);

        mockMvc.perform(delete("/income/{incomeId}", incomeId)
                        .with(csrf()))
                .andExpect(status().isOk());

        verify(incomeService, times(1)).deleteIncome(incomeId);
    }

    @Test
    @WithMockUser(username = "joel")
    void testShouldUpdateIncome() throws Exception{
        UUID incomeId = UUID.randomUUID();

        IncomeData dto = IncomeData.builder()
                .title("Title")
                .amount(BigDecimal.valueOf(99))
                .incomeDate(LocalDate.now())
                .build();

        mockMvc.perform(MockMvcRequestBuilders.patch("/income/{incomeId}", incomeId)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "joel")
    void testShouldRetrieveSingleIncome() throws Exception{
        IncomeData incomeData = createIncomeData();

        when(incomeService.getIncomeDetails(incomeData.getIncomeId()))
                .thenReturn(incomeData);

        mockMvc.perform(get("/income/{incomeId}", incomeData.getIncomeId())
                .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value(incomeData.getTitle()))
                .andExpect(jsonPath("$.amount").value(incomeData.getAmount()))
                .andExpect(jsonPath("$.incomeDate").value(incomeData.getIncomeDate().toString()));
    }
}
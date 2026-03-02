package de.ExpenseTracker.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ResponseData {
    private String username;
    private String messageKey;
}
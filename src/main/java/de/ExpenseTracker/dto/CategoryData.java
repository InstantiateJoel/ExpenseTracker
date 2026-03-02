package de.ExpenseTracker.dto;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class CategoryData {
    private UUID categoryId;
    private String localizedName;
}
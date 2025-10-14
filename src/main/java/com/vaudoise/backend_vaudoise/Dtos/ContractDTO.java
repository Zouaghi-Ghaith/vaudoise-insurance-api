package com.vaudoise.backend_vaudoise.Dtos;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
public class ContractDTO {

    private Long id;
    private LocalDate startDate;
    private LocalDate endDate;

    @NotNull(message = "Cost amount is required")
    @Positive(message = "Cost amount must be positive")
    private BigDecimal costAmount;

    @AssertTrue(message = "End date must be after start date")
    private boolean isEndDateValid() {
        if (endDate == null || startDate == null) return true;
        return !endDate.isBefore(startDate);
    }

}

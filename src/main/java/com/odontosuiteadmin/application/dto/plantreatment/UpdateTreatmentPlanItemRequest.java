package com.odontosuiteadmin.application.dto.plantreatment;

import com.odontosuiteadmin.domain.model.enums.TreatmentStatus;
import jakarta.validation.constraints.DecimalMin;

import java.math.BigDecimal;

public record UpdateTreatmentPlanItemRequest(
        @DecimalMin(value = "0.00", message = "finalCost debe ser >= 0")
        BigDecimal finalCost,
        String notes,
        TreatmentStatus status
) {}


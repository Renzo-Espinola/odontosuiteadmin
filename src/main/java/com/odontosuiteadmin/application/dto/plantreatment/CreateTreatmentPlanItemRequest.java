package com.odontosuiteadmin.application.dto.plantreatment;

import com.odontosuiteadmin.domain.model.enums.ToothSurface;
import com.odontosuiteadmin.domain.model.enums.TreatmentProcedure;
import com.odontosuiteadmin.domain.model.enums.TreatmentStatus;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record CreateTreatmentPlanItemRequest(
        @NotNull Long patientId,
        String toothCode,
        ToothSurface surface,
        @NotNull TreatmentProcedure procedure,
        TreatmentStatus status,             // opcional, default PLANNED
        @NotNull BigDecimal estimatedCost,
        String notes)
{}

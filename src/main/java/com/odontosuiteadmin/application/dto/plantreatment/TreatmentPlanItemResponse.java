package com.odontosuiteadmin.application.dto.plantreatment;

import com.odontosuiteadmin.domain.model.enums.ToothSurface;
import com.odontosuiteadmin.domain.model.enums.TreatmentProcedure;
import com.odontosuiteadmin.domain.model.enums.TreatmentStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record TreatmentPlanItemResponse(Long id,
                                        Long patientId,
                                        String toothCode,
                                        ToothSurface surface,
                                        TreatmentProcedure procedure,
                                        TreatmentStatus status,
                                        BigDecimal estimatedCost,
                                        BigDecimal finalCost,
                                        String notes,
                                        LocalDateTime completedAt,
                                        LocalDateTime createdAt,
                                        LocalDateTime updatedAt) {
}

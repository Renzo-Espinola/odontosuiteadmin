package com.odontosuiteadmin.application.dto.plantreatment;

import com.odontosuiteadmin.domain.model.enums.TreatmentStatus;
import jakarta.validation.constraints.NotNull;

public record UpdateTreatmentPlanStatusRequest(@NotNull TreatmentStatus status) {
}

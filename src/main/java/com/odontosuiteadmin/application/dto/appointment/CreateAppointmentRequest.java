package com.odontosuiteadmin.application.dto.appointment;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

public record CreateAppointmentRequest(
        @NotNull Long patientId,
        @NotNull LocalDateTime startTime,
        LocalDateTime endTime,
        String reason,
        String notes) {
}

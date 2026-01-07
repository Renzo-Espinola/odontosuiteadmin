package com.odontosuiteadmin.application.dto.appointment;

import com.odontosuiteadmin.domain.model.enums.AppointmentStatus;
import java.time.LocalDateTime;

public record AppointmentResponse(
        Long id,
        Long patientId,
        LocalDateTime startTime,
        LocalDateTime endTime,
        AppointmentStatus status,
        String reason,
        String notes) {
}

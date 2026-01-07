package com.odontosuiteadmin.application.dto.appointment;

import com.odontosuiteadmin.domain.model.enums.AppointmentStatus;
import jakarta.validation.constraints.NotNull;

public record UpdateAppointmentStatusRequest(
        @NotNull AppointmentStatus status) {
}

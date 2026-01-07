package com.odontosuiteadmin.application.dto.cash;

import com.odontosuiteadmin.domain.model.enums.MovementNature;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public record MoneyMovementResponse(
        Long id,
        MovementNature movementNature,
        BigDecimal amount,
        String currency,
        Long patientId,
        Long appointmentId,
        String description,
        LocalDateTime createdAt) {
}

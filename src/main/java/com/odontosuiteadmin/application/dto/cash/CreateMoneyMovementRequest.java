package com.odontosuiteadmin.application.dto.cash;

import com.odontosuiteadmin.domain.model.enums.MovementConcept;
import com.odontosuiteadmin.domain.model.enums.PaymentMethod;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;

public record CreateMoneyMovementRequest(
        @NotNull MovementConcept concept,
        @NotNull PaymentMethod paymentMethod,
        @NotNull @Positive BigDecimal amount,
        Long patientId,
        Long appointmentId,
        String description) {
}

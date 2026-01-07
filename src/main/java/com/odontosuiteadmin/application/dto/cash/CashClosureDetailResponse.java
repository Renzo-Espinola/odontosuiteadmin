package com.odontosuiteadmin.application.dto.cash;

import com.odontosuiteadmin.domain.model.enums.MovementNature;
import com.odontosuiteadmin.domain.model.enums.PaymentMethod;
import java.math.BigDecimal;

public record CashClosureDetailResponse(
        MovementNature type,
        PaymentMethod paymentMethod,
        BigDecimal totalAmount,
        long movementCount) {
}

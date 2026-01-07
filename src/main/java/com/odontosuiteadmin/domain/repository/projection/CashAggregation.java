package com.odontosuiteadmin.domain.repository.projection;

import com.odontosuiteadmin.domain.model.enums.MovementNature;
import com.odontosuiteadmin.domain.model.enums.PaymentMethod;
import java.math.BigDecimal;

public interface CashAggregation {
    MovementNature getType();

    PaymentMethod getPaymentMethod();

    BigDecimal getTotalAmount();

    long getMovementCount();
}

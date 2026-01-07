package com.odontosuiteadmin.application.dto.reports;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record CashSummaryResponse(
        LocalDateTime from,
        LocalDateTime to,
        BigDecimal totalIncome,
        BigDecimal totalExpense,
        BigDecimal netTotal) {
}

package com.odontosuiteadmin.application.dto.cash;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public record CashClosureResponse(
        Long id,
        LocalDate closureDate,
        BigDecimal totalIncome,
        BigDecimal totalExpense,
        BigDecimal netTotal,
        List<CashClosureDetailResponse> details) {
}

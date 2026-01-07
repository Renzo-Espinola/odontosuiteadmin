package com.odontosuiteadmin.web.controller;

import com.odontosuiteadmin.application.dto.reports.CashSummaryResponse;
import com.odontosuiteadmin.domain.model.enums.MovementNature;
import com.odontosuiteadmin.domain.repository.MoneyMovementRepository;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/reports")
@RequiredArgsConstructor
public class ReportsController {
    private final MoneyMovementRepository repo;

    @GetMapping("/cash/summary")
    public CashSummaryResponse cashSummary(
            @RequestParam LocalDateTime from,
            @RequestParam LocalDateTime to) {

        BigDecimal income = repo.sumByTypeBetween(MovementNature.INCOME, from, to);
        BigDecimal expense = repo.sumByTypeBetween(MovementNature.EXPENSE, from, to);
        return new CashSummaryResponse(from, to, income, expense, income.subtract(expense));
    }
}

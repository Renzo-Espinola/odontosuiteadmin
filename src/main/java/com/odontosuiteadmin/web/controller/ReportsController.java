package com.odontosuiteadmin.web.controller;

import com.odontosuiteadmin.application.dto.reports.CashSummaryResponse;
import com.odontosuiteadmin.application.dto.security.MeDto;
import com.odontosuiteadmin.domain.model.enums.MovementNature;
import com.odontosuiteadmin.domain.repository.MoneyMovementRepository;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
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

    @GetMapping("/me")
    public MeDto me(@AuthenticationPrincipal Jwt jwt) {
        long userId = Long.parseLong(jwt.getSubject());
        long clinicId = jwt.getClaim("activeClinicId");
        String role = jwt.getClaim("activeRole");

        return new MeDto(userId, clinicId, role);
    }
}

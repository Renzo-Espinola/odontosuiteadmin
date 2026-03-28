package com.odontosuiteadmin.web.controller;

import com.odontosuiteadmin.application.dto.cash.CashClosureResponse;
import com.odontosuiteadmin.application.dto.cash.CreateCashClosureRequest;
import com.odontosuiteadmin.application.dto.security.MeDto;
import com.odontosuiteadmin.application.service.clashclosure.CashClosureService;
import jakarta.validation.Valid;
import java.security.Principal;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cash/closures")
@RequiredArgsConstructor
public class CashClosureController {
    private final CashClosureService service;

    @PostMapping
    public CashClosureResponse closeDay(@Valid @RequestBody CreateCashClosureRequest request, Principal principal) {
        String createdBy = (principal != null ? principal.getName() : "system");
        return service.closeDay(request, createdBy);
    }

    @GetMapping("/{date}")
    public CashClosureResponse getByDate(@PathVariable LocalDate date) {
        return service.getByDate(date);
    }

    @GetMapping
    public List<CashClosureResponse> list() {
        return service.list();
    }

    @GetMapping("/me")
    public MeDto me(@AuthenticationPrincipal Jwt jwt) {
        long userId = Long.parseLong(jwt.getSubject());
        long clinicId = jwt.getClaim("activeClinicId");
        String role = jwt.getClaim("activeRole");

        return new MeDto(userId, clinicId, role);
    }
}

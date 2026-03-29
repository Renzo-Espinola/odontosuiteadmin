package com.odontosuiteadmin.web.controller;

import com.odontosuiteadmin.application.dto.cash.CreateMoneyMovementRequest;
import com.odontosuiteadmin.application.dto.cash.MoneyMovementResponse;
import com.odontosuiteadmin.application.dto.security.MeDto;
import com.odontosuiteadmin.application.service.money.MoneyMovementService;
import jakarta.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cash")
@RequiredArgsConstructor
public class CashController {
    private final MoneyMovementService service;

    @PostMapping("/movements")
    public MoneyMovementResponse create(@Valid @RequestBody CreateMoneyMovementRequest request) {
        return service.create(request);
    }

    @GetMapping("/movements")
    public List<MoneyMovementResponse> list(
            @RequestParam LocalDateTime from,
            @RequestParam LocalDateTime to) {
        return service.list(from, to);
    }

    @GetMapping("/movements/patient/{patientId}")
    public List<MoneyMovementResponse> listByPatient(@PathVariable Long patientId) {
        return service.listByPatient(patientId);
    }

    @GetMapping("/me")
    public MeDto me(@AuthenticationPrincipal Jwt jwt) {
        long userId = Long.parseLong(jwt.getSubject());
        long clinicId = jwt.getClaim("activeClinicId");
        String role = jwt.getClaim("activeRole");

        return new MeDto(userId, clinicId, role);
    }

}

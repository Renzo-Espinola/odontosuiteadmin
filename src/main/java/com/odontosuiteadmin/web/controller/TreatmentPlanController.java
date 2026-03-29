package com.odontosuiteadmin.web.controller;

import com.odontosuiteadmin.application.dto.plantreatment.CreateTreatmentPlanItemRequest;
import com.odontosuiteadmin.application.dto.plantreatment.TreatmentPlanItemResponse;
import com.odontosuiteadmin.application.dto.plantreatment.UpdateTreatmentPlanItemRequest;
import com.odontosuiteadmin.application.dto.plantreatment.UpdateTreatmentPlanStatusRequest;
import com.odontosuiteadmin.application.dto.security.MeDto;
import com.odontosuiteadmin.application.service.plantreatment.TreatmentPlanService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/treatment-plans")
@RequiredArgsConstructor
public class TreatmentPlanController {

    private final TreatmentPlanService service;

    @PostMapping
    public TreatmentPlanItemResponse create(@Valid @RequestBody CreateTreatmentPlanItemRequest request) {
        return service.create(request);
    }

    @GetMapping("/patient/{patientId}")
    public List<TreatmentPlanItemResponse> listByPatient(@PathVariable Long patientId) {
        return service.listByPatient(patientId);
    }

    @PatchMapping("/{id}/status")
    public TreatmentPlanItemResponse updateStatus(
            @PathVariable Long id,
            @Valid @RequestBody UpdateTreatmentPlanStatusRequest request) {
        return service.updateStatus(id, request.status());
    }

    @PutMapping("/{id}")
    public TreatmentPlanItemResponse update(@PathVariable Long id, @Valid @RequestBody UpdateTreatmentPlanItemRequest r) {
        return service.update(id, r);
    }

    @GetMapping("/me")
    public MeDto me(@AuthenticationPrincipal Jwt jwt) {
        long userId = Long.parseLong(jwt.getSubject());
        long clinicId = jwt.getClaim("activeClinicId");
        String role = jwt.getClaim("activeRole");

        return new MeDto(userId, clinicId, role);
    }

}

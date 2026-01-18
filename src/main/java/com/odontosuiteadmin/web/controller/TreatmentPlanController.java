package com.odontosuiteadmin.web.controller;

import com.odontosuiteadmin.application.dto.plantreatment.CreateTreatmentPlanItemRequest;
import com.odontosuiteadmin.application.dto.plantreatment.TreatmentPlanItemResponse;
import com.odontosuiteadmin.application.service.plantreatment.TreatmentPlanService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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

}

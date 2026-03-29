package com.odontosuiteadmin.web.controller;

import com.odontosuiteadmin.application.dto.appointment.AppointmentResponse;
import com.odontosuiteadmin.application.dto.appointment.CreateAppointmentRequest;
import com.odontosuiteadmin.application.dto.appointment.UpdateAppointmentStatusRequest;
import com.odontosuiteadmin.application.dto.security.MeDto;
import com.odontosuiteadmin.application.service.appointment.AppointmentService;
import jakarta.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/appointments")
@RequiredArgsConstructor
public class AppointmentController {
    private final AppointmentService service;

    @PostMapping
    public AppointmentResponse create(@Valid @RequestBody CreateAppointmentRequest request) {
        return service.create(request);
    }

    @PatchMapping("/{id}/status")
    public AppointmentResponse updateStatus(
            @PathVariable Long id,
            @Valid @RequestBody UpdateAppointmentStatusRequest request) {
        return service.updateStatus(id, request);
    }

    @GetMapping
    public List<AppointmentResponse> list(
            @RequestParam LocalDateTime from,
            @RequestParam LocalDateTime to) {
        return service.list(from, to);
    }

    @GetMapping("/patient/{patientId}")
    public List<AppointmentResponse> listByPatient(@PathVariable Long patientId) {
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

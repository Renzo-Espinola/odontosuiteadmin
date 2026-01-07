package com.odontosuiteadmin.application.service.appointment;

import com.odontosuiteadmin.application.dto.appointment.AppointmentResponse;
import com.odontosuiteadmin.application.dto.appointment.CreateAppointmentRequest;
import com.odontosuiteadmin.application.dto.appointment.UpdateAppointmentStatusRequest;
import java.time.LocalDateTime;
import java.util.List;

public interface AppointmentService {
    AppointmentResponse create(CreateAppointmentRequest request);

    AppointmentResponse updateStatus(Long id, UpdateAppointmentStatusRequest request);

    List<AppointmentResponse> list(LocalDateTime from, LocalDateTime to);

    List<AppointmentResponse> listByPatient(Long patientId);
}

package com.odontosuiteadmin.application.service.appointment;
import com.odontosuiteadmin.application.dto.appointment.*;
import com.odontosuiteadmin.domain.model.entity.Appointment;
import com.odontosuiteadmin.domain.repository.AppointmentRepository;
import jakarta.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class AppointmentServiceImpl implements AppointmentService {

    private final AppointmentRepository repository;

    @Override
    public AppointmentResponse create(CreateAppointmentRequest r) {
        Appointment appointment = Appointment.builder()
                .patientId(r.patientId())
                .startTime(r.startTime())
                .endTime(r.endTime())
                .reason(r.reason())
                .notes(r.notes())
                .build();

        return toResponse(repository.save(appointment));
    }

    @Override
    public AppointmentResponse updateStatus(Long id, UpdateAppointmentStatusRequest r) {
        Appointment appointment = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Turno no encontrado"));

        appointment.setStatus(r.status());
        return toResponse(repository.save(appointment));
    }

    @Override
    @Transactional(readOnly = true)
    public List<AppointmentResponse> list(LocalDateTime from, LocalDateTime to) {
        return repository.findByStartTimeBetween(from, to)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<AppointmentResponse> listByPatient(Long patientId) {
        return repository.findByPatientId(patientId)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    private AppointmentResponse toResponse(Appointment a) {
        return new AppointmentResponse(
                a.getId(),
                a.getPatientId(),
                a.getStartTime(),
                a.getEndTime(),
                a.getStatus(),
                a.getReason(),
                a.getNotes());
    }
}

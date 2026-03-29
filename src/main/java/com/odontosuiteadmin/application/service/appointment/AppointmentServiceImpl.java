package com.odontosuiteadmin.application.service.appointment;
import com.odontosuiteadmin.application.dto.appointment.*;
import com.odontosuiteadmin.domain.model.entity.Appointment;
import com.odontosuiteadmin.domain.model.enums.AppointmentStatus;
import com.odontosuiteadmin.domain.repository.AppointmentRepository;
import jakarta.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
@Transactional
public class AppointmentServiceImpl implements AppointmentService {

    private static final int DEFAULT_DURATION_MIN = 30;
    private static final int MAX_PAST_DAYS = 30;

    private final AppointmentRepository repository;

    @Override
    public AppointmentResponse create(CreateAppointmentRequest r) {
        LocalDateTime start = r.startTime();
        LocalDateTime end = (r.endTime() != null) ? r.endTime() : start.plusMinutes(DEFAULT_DURATION_MIN);

        if (end.isBefore(start)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "endTime must be after startTime");
        }

        LocalDateTime now = LocalDateTime.now();
        boolean createdLate = start.isBefore(now);

        if (!createdLate) {
            boolean overlaps = !repository.findOverlaps(start, end).isEmpty();
            if (overlaps) {
                throw new ResponseStatusException(HttpStatus.CONFLICT, "Ya existe un turno en ese horario");
            }
        }

        Appointment appointment = Appointment.builder()
                .patientId(r.patientId())
                .startTime(start)
                .endTime(end)
                .reason(r.reason())
                .notes(r.notes())
                .createdLate(createdLate)
                .status(createdLate ? AppointmentStatus.COMPLETED : AppointmentStatus.SCHEDULED)
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
                a.getNotes(),
                a.isCreatedLate(),
                a.getCreatedAt(),
                a.getUpdatedAt());
    }
}

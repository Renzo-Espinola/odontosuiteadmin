package com.odontosuiteadmin.domain.repository;

import com.odontosuiteadmin.domain.model.entity.Appointment;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    List<Appointment> findByPatientId(Long patientId);

    List<Appointment> findByStartTimeBetween(LocalDateTime from, LocalDateTime to);
}

package com.odontosuiteadmin.domain.repository;

import com.odontosuiteadmin.domain.model.entity.Appointment;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    List<Appointment> findByPatientId(Long patientId);

    List<Appointment> findByStartTimeBetween(LocalDateTime from, LocalDateTime to);

    @Query("""
            select a from Appointment a
            where a.status <> com.odontosuiteadmin.domain.model.enums.AppointmentStatus.CANCELLED
              and a.startTime < :end
              and (a.endTime is null or a.endTime > :start)
            """)
    List<Appointment> findOverlaps(@Param("start") LocalDateTime start,
            @Param("end") LocalDateTime end);
}

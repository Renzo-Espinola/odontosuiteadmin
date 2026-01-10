package com.odontosuiteadmin.domain.model.entity;

import com.odontosuiteadmin.domain.model.enums.AppointmentStatus;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name = "appointments",
        indexes = {
                @Index(name = "idx_appointments_patient", columnList = "patient_id"),
                @Index(name = "idx_appointments_start_time", columnList = "start_time"),
                @Index(name = "idx_appointments_created_at", columnList = "created_at")
        })
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "patient_id", nullable = false)
    private Long patientId;

    @Column(name = "start_time", nullable = false)
    private LocalDateTime startTime;

    @Column(name = "end_time")
    private LocalDateTime endTime;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Builder.Default
    private AppointmentStatus status = AppointmentStatus.SCHEDULED;

    private String reason;
    private String notes;

    @Column(name = "created_late", nullable = false)
    @Builder.Default
    private boolean createdLate = false;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;
}

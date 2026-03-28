package com.odontosuiteadmin.domain.model.entity;

import com.odontosuiteadmin.config.ClinicEntityListener;
import com.odontosuiteadmin.domain.model.enums.ToothSurface;
import com.odontosuiteadmin.domain.model.enums.TreatmentProcedure;
import com.odontosuiteadmin.domain.model.enums.TreatmentStatus;
import jakarta.persistence.*;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(
        name = "treatment_plan_items",
        indexes = {
                @Index(name = "idx_tpi_patient", columnList = "patient_id"),
                @Index(name = "idx_tpi_status", columnList = "status"),
                @Index(name = "idx_tpi_created_at", columnList = "created_at")
        }
)
@Getter
@Setter
@Filter(name = "clinicFilter", condition = "clinic_id = :clinicId")
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class TreatmentPlanItem extends TenantEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "patient_id", nullable = false)
    private Long patientId;

    // opcional (ej: limpieza/blanqueamiento)
    @Column(name = "tooth_code", length = 4)
    private String toothCode;

    @Enumerated(EnumType.STRING)
    @Column(name = "surface", length = 10)
    private ToothSurface surface;

    @Enumerated(EnumType.STRING)
    @Column(name = "procedure", nullable = false, length = 30)
    private TreatmentProcedure procedure;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    @Builder.Default
    private TreatmentStatus status = TreatmentStatus.PLANNED;

    @Column(name = "estimated_cost", nullable = false, precision = 18, scale = 2)
    private BigDecimal estimatedCost;

    @Column(name = "final_cost", precision = 18, scale = 2)
    private BigDecimal finalCost;

    @Column(name = "notes", length = 1000)
    private String notes;

    @Column(name = "completed_at")
    private LocalDateTime completedAt;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

}

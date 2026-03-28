package com.odontosuiteadmin.domain.model.entity;
import com.odontosuiteadmin.domain.model.enums.MovementConcept;
import com.odontosuiteadmin.domain.model.enums.MovementNature;
import com.odontosuiteadmin.domain.model.enums.PaymentMethod;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.Filter;

@Entity
@Table(name = "money_movements",
        indexes = {
                @Index(name = "idx_movements_patient", columnList = "patient_id"),
                @Index(name = "idx_movements_time", columnList = "created_at")
        })
@Getter
@Setter
@Filter(name = "clinicFilter", condition = "clinic_id = :clinicId")
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class MoneyMovement extends TenantEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "amount", nullable = false, precision = 18, scale = 2)
    private BigDecimal amount;

    @Column(name = "currency", nullable = false, length = 3)
    @Builder.Default
    private String currency = "ARS";

    @Column(name = "patient_id")
    private Long patientId;

    @Column(name = "appointment_id")
    private Long appointmentId;

    private String description;

    @Column(name = "created_at", nullable = false)
    @Builder.Default
    private LocalDateTime createdAt = LocalDateTime.now();

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_method", nullable = false)
    private PaymentMethod paymentMethod;

    @Enumerated(EnumType.STRING)
    @Column(name = "nature", nullable = false)
    private MovementNature nature;

    @Enumerated(EnumType.STRING)
    @Column(name = "concept", nullable = false)
    private MovementConcept concept;

}

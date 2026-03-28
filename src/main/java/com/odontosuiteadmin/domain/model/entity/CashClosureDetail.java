package com.odontosuiteadmin.domain.model.entity;

import com.odontosuiteadmin.config.ClinicEntityListener;
import com.odontosuiteadmin.domain.model.enums.MovementNature;
import com.odontosuiteadmin.domain.model.enums.PaymentMethod;
import jakarta.persistence.*;
import java.math.BigDecimal;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.ParamDef;

@Entity
@Table(name = "cash_closure_details",
        uniqueConstraints =
        @UniqueConstraint(name="uk_cash_closure_date",
                columnNames={"clinic_id","closure_date"}))

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Filter(name = "clinicFilter", condition = "clinic_id = :clinicId")
public class CashClosureDetail extends TenantEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "closure_id")
    private CashClosure closure;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MovementNature type;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_method", nullable = false)
    private PaymentMethod paymentMethod;

    @Column(name = "total_amount", nullable = false, precision = 18, scale = 2)
    private BigDecimal totalAmount;

    @Column(name = "movement_count", nullable = false)
    private long movementCount;
}

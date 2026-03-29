package com.odontosuiteadmin.domain.model.entity;

import com.odontosuiteadmin.config.ClinicEntityListener;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.ParamDef;

@Entity
@Table(name = "cash_closures",
        uniqueConstraints = @UniqueConstraint(name = "uk_cash_closure_date", columnNames = "closure_date"))
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Filter(name = "clinicFilter", condition = "clinic_id = :clinicId")
public class CashClosure extends TenantEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "closure_date", nullable = false)
    private LocalDate closureDate;

    @Column(name = "from_ts", nullable = false)
    private LocalDateTime fromTs;

    @Column(name = "to_ts", nullable = false)
    private LocalDateTime toTs;

    @Column(name = "total_income", nullable = false, precision = 18, scale = 2)
    private BigDecimal totalIncome;

    @Column(name = "total_expense", nullable = false, precision = 18, scale = 2)
    private BigDecimal totalExpense;

    @Column(name = "net_total", nullable = false, precision = 18, scale = 2)
    private BigDecimal netTotal;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "created_by", length = 100)
    private String createdBy;

    @OneToMany(mappedBy = "closure", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<CashClosureDetail> details = new ArrayList<>();

}

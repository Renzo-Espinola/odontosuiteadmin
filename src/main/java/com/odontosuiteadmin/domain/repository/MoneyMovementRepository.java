package com.odontosuiteadmin.domain.repository;

import com.odontosuiteadmin.domain.model.entity.MoneyMovement;
import com.odontosuiteadmin.domain.model.enums.MovementNature;
import com.odontosuiteadmin.domain.repository.projection.CashAggregation;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MoneyMovementRepository extends JpaRepository<MoneyMovement, Long> {
    List<MoneyMovement> findByCreatedAtBetween(LocalDateTime from, LocalDateTime to);

    List<MoneyMovement> findByPatientId(Long patientId);

    @Query("""
            select coalesce(sum(m.amount), 0)
            from MoneyMovement m
            where m.nature = :nature and m.createdAt >= :from and m.createdAt < :to
            """)
    BigDecimal sumByTypeBetween(MovementNature nature, LocalDateTime from, LocalDateTime to);

    boolean existsByCreatedAtBetween(LocalDateTime from, LocalDateTime to);

    @Query("""
                select
                    m.nature as nature,
                    m.paymentMethod as paymentMethod,
                    sum(m.amount) as totalAmount,
                    count(m) as movementCount
                from MoneyMovement m
                where m.createdAt >= :from and m.createdAt < :to
                group by m.nature, m.paymentMethod
            """)
    List<CashAggregation> aggregateByTypeAndPayment(LocalDateTime from, LocalDateTime to);

}

package com.odontosuiteadmin.domain.repository;

import com.odontosuiteadmin.domain.model.entity.CashClosure;
import java.time.LocalDate;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CashClosureRepository extends JpaRepository<CashClosure, Long> {
    Optional<CashClosure> findByClosureDate(LocalDate date);

    boolean existsByClosureDate(LocalDate date);
}

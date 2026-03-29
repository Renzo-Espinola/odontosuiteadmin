package com.odontosuiteadmin.application.service.money;

import com.odontosuiteadmin.application.dto.cash.CreateMoneyMovementRequest;
import com.odontosuiteadmin.application.dto.cash.MoneyMovementResponse;
import com.odontosuiteadmin.domain.model.entity.MoneyMovement;
import com.odontosuiteadmin.domain.model.enums.MovementConcept;
import com.odontosuiteadmin.domain.model.enums.MovementNature;
import com.odontosuiteadmin.domain.repository.CashClosureRepository;
import com.odontosuiteadmin.domain.repository.MoneyMovementRepository;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class MoneyMovementServiceImpl implements MoneyMovementService {

    private final MoneyMovementRepository repository;
    private final CashClosureRepository cashClosureRepository;

    @Override
    public MoneyMovementResponse create(CreateMoneyMovementRequest r) {

        validateRequest(r);
        validateCashNotClosedToday();

        MoneyMovement movement = MoneyMovement.builder()
                .nature(r.concept().nature())
                .concept(r.concept())
                .amount(r.amount())
                .patientId(r.patientId())
                .appointmentId(r.appointmentId())
                .description(r.description())
                .paymentMethod(r.paymentMethod())
                .build();

        return toResponse(repository.save(movement));
    }

    @Override
    @Transactional(readOnly = true)
    public List<MoneyMovementResponse> list(LocalDateTime from, LocalDateTime to) {
        return repository.findByCreatedAtBetween(from, to)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<MoneyMovementResponse> listByPatient(Long patientId) {
        return repository.findByPatientId(patientId)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    private void validateRequest(CreateMoneyMovementRequest r) {
        if (r == null)
            throw new IllegalArgumentException("request es requerido");
        if (r.concept() == null)
            throw new IllegalArgumentException("concept es requerido");
        if (r.paymentMethod() == null)
            throw new IllegalArgumentException("paymentMethod es requerido");
        if (r.amount() == null)
            throw new IllegalArgumentException("amount es requerido");

        MovementNature nature = r.concept().nature();

        if (nature == MovementNature.INCOME
                && r.concept() != MovementConcept.OTHER_INCOME
                && r.patientId() == null) {
            throw new IllegalArgumentException("patientId es requerido para ingresos (excepto OTHER_INCOME)");
        }

        if (r.appointmentId() != null && r.patientId() == null) {
            throw new IllegalArgumentException("patientId es requerido cuando appointmentId está presente");
        }
    }

    private void validateCashNotClosedToday() {
        LocalDate today = LocalDate.now();
        if (cashClosureRepository.existsByClosureDate(today)) {
            throw new IllegalStateException("La caja de hoy ya está cerrada, no se pueden registrar movimientos");
        }
    }

    private MoneyMovementResponse toResponse(MoneyMovement m) {
        return new MoneyMovementResponse(
                m.getId(),
                m.getNature(),
                m.getAmount(),
                m.getCurrency(),
                m.getPatientId(),
                m.getAppointmentId(),
                m.getDescription(),
                m.getCreatedAt());
    }

}

package com.odontosuiteadmin.application.service.clashclosure;

import com.odontosuiteadmin.application.dto.cash.CashClosureDetailResponse;
import com.odontosuiteadmin.application.dto.cash.CashClosureResponse;
import com.odontosuiteadmin.application.dto.cash.CreateCashClosureRequest;
import com.odontosuiteadmin.domain.model.entity.CashClosure;
import com.odontosuiteadmin.domain.model.entity.CashClosureDetail;
import com.odontosuiteadmin.domain.model.enums.MovementNature;
import com.odontosuiteadmin.domain.repository.CashClosureRepository;
import com.odontosuiteadmin.domain.repository.MoneyMovementRepository;
import com.odontosuiteadmin.domain.repository.projection.CashAggregation;
import jakarta.persistence.EntityNotFoundException;
import java.math.BigDecimal;
import java.time.*;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class CashClosureServiceImpl implements CashClosureService {

    private final CashClosureRepository closureRepository;
    private final MoneyMovementRepository movementRepository;

    @Override
    public CashClosureResponse closeDay(CreateCashClosureRequest request, String createdBy) {
        LocalDate date = request.date();

        if (closureRepository.existsByClosureDate(date)) {
            throw new IllegalStateException("La caja ya está cerrada para " + date);
        }

        LocalDateTime from = date.atStartOfDay();
        LocalDateTime to = date.plusDays(1).atStartOfDay();

        List<CashAggregation> aggregations = movementRepository.aggregateByTypeAndPayment(from, to);

        BigDecimal totalIncome = BigDecimal.ZERO;
        BigDecimal totalExpense = BigDecimal.ZERO;

        CashClosure closure = CashClosure.builder()
                .closureDate(date)
                .fromTs(from)
                .toTs(to)
                .createdAt(LocalDateTime.now())
                .createdBy(createdBy)
                .build();

        for (CashAggregation a : aggregations) {
            if (a.getType() == MovementNature.INCOME) {
                totalIncome = totalIncome.add(a.getTotalAmount());
            } else {
                totalExpense = totalExpense.add(a.getTotalAmount());
            }

            CashClosureDetail detail = CashClosureDetail.builder()
                    .closure(closure)
                    .type(a.getType())
                    .paymentMethod(a.getPaymentMethod())
                    .totalAmount(a.getTotalAmount())
                    .movementCount(a.getMovementCount())
                    .build();

            closure.getDetails().add(detail);
        }

        closure.setTotalIncome(totalIncome);
        closure.setTotalExpense(totalExpense);
        closure.setNetTotal(totalIncome.subtract(totalExpense));

        return toResponse(closureRepository.save(closure));
    }

    @Override
    @Transactional(readOnly = true)
    public CashClosureResponse getByDate(LocalDate date) {
        CashClosure closure = closureRepository.findByClosureDate(date)
                .orElseThrow(() -> new EntityNotFoundException("No existe cierre para: " + date));
        return toResponse(closure);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CashClosureResponse> list() {
        return closureRepository.findAll().stream().map(this::toResponse).toList();
    }

    private CashClosureResponse toResponse(CashClosure c) {
        return new CashClosureResponse(
                c.getId(),
                c.getClosureDate(),
                c.getTotalIncome(),
                c.getTotalExpense(),
                c.getNetTotal(),
                c.getDetails().stream()
                        .map(d -> new CashClosureDetailResponse(
                                d.getType(),
                                d.getPaymentMethod(),
                                d.getTotalAmount(),
                                d.getMovementCount()))
                        .toList());
    }
}

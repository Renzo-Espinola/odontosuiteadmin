package com.odontosuiteadmin.application.service.plantreatment;

import com.odontosuiteadmin.application.dto.plantreatment.CreateTreatmentPlanItemRequest;
import com.odontosuiteadmin.application.dto.plantreatment.TreatmentPlanItemResponse;
import com.odontosuiteadmin.domain.model.entity.TreatmentPlanItem;
import com.odontosuiteadmin.domain.model.enums.TreatmentStatus;
import com.odontosuiteadmin.domain.repository.TreatmentPlanRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class TreatmentPlanServiceImpl implements TreatmentPlanService {

    private final TreatmentPlanRepository repo;

    @Override
    public TreatmentPlanItemResponse create(CreateTreatmentPlanItemRequest r) {
        validate(r);

        TreatmentPlanItem item = TreatmentPlanItem.builder()
                .patientId(r.patientId())
                .toothCode(trimToNull(r.toothCode()))
                .surface(r.surface())
                .procedure(r.procedure())
                .status(r.status() != null ? r.status() : TreatmentStatus.PLANNED)
                .estimatedCost(r.estimatedCost())
                .notes(trimToNull(r.notes()))
                .build();

        return toResponse(repo.save(item));
    }

    @Override
    @Transactional(readOnly = true)
    public List<TreatmentPlanItemResponse> listByPatient(Long patientId) {
        return repo.findByPatientIdOrderByCreatedAtDesc(patientId)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    private void validate(CreateTreatmentPlanItemRequest r) {
        if (r == null) throw new IllegalArgumentException("request es requerido");
        if (r.patientId() == null) throw new IllegalArgumentException("patientId es requerido");
        if (r.procedure() == null) throw new IllegalArgumentException("procedure es requerido");
        if (r.estimatedCost() == null) throw new IllegalArgumentException("estimatedCost es requerido");
        if (r.estimatedCost().compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("estimatedCost debe ser >= 0");
        }
        // Si mandan surface, toothCode debería venir (consistencia)
        if (r.surface() != null && (r.toothCode() == null || r.toothCode().isBlank())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "toothCode es requerido cuando surface está presente");
        }
    }

    private TreatmentPlanItemResponse toResponse(TreatmentPlanItem t) {
        return new TreatmentPlanItemResponse(
                t.getId(),
                t.getPatientId(),
                t.getToothCode(),
                t.getSurface(),
                t.getProcedure(),
                t.getStatus(),
                t.getEstimatedCost(),
                t.getFinalCost(),
                t.getNotes(),
                t.getCompletedAt(),
                t.getCreatedAt(),
                t.getUpdatedAt()
        );
    }

    private String trimToNull(String s) {
        if (s == null) return null;
        String x = s.trim();
        return x.isEmpty() ? null : x;
    }
}

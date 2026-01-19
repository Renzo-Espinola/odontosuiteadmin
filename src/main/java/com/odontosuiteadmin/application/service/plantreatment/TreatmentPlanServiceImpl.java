package com.odontosuiteadmin.application.service.plantreatment;

import com.odontosuiteadmin.application.dto.plantreatment.CreateTreatmentPlanItemRequest;
import com.odontosuiteadmin.application.dto.plantreatment.TreatmentPlanItemResponse;
import com.odontosuiteadmin.application.dto.plantreatment.UpdateTreatmentPlanItemRequest;
import com.odontosuiteadmin.domain.model.entity.TreatmentPlanItem;
import com.odontosuiteadmin.domain.model.enums.TreatmentStatus;
import com.odontosuiteadmin.domain.repository.TreatmentPlanRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Transactional
public class TreatmentPlanServiceImpl implements TreatmentPlanService {

    private final TreatmentPlanRepository repo;

    private static final Set<String> VALID_TEETH = Set.of(
            "11","12","13","14","15","16","17","18",
            "21","22","23","24","25","26","27","28",
            "31","32","33","34","35","36","37","38",
            "41","42","43","44","45","46","47","48"
    );


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

    @Override
    public TreatmentPlanItemResponse updateStatus(Long id, TreatmentStatus status) {
        TreatmentPlanItem item = repo.findById(id)
                                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        item.setStatus(status);

        if (status == TreatmentStatus.COMPLETED && item.getCompletedAt() == null) {
            item.setCompletedAt(LocalDateTime.now());
        }
        if (status == TreatmentStatus.CANCELLED) {
            item.setCompletedAt(null);
        }

        return toResponse(repo.save(item));
    }

    @Override
    public TreatmentPlanItemResponse update(Long id, UpdateTreatmentPlanItemRequest r) {
        TreatmentPlanItem item = repo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        item.setNotes(trimToNull(r.notes()));

        if (r.finalCost() != null) {
            if (r.finalCost().compareTo(BigDecimal.ZERO) < 0) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "finalCost debe ser >= 0");
            }
            item.setFinalCost(r.finalCost());
        }

        if (r.status() != null) {
            item.setStatus(r.status());
            if (r.status() == TreatmentStatus.COMPLETED && item.getCompletedAt() == null) {
                item.setCompletedAt(LocalDateTime.now());
            }

            if (r.status() == TreatmentStatus.CANCELLED) {
                item.setCompletedAt(null);
            }
        }

        return toResponse(repo.save(item));
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

        if (r.toothCode() != null) {
            String tooth = r.toothCode().trim();
            if (!VALID_TEETH.contains(tooth)) {
                throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST,
                        "toothCode inválido según nomenclatura FDI: " + tooth
                );
            }
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

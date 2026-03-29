package com.odontosuiteadmin.domain.repository;

import com.odontosuiteadmin.domain.model.entity.TreatmentPlanItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TreatmentPlanRepository extends JpaRepository<TreatmentPlanItem, Long> {
    List<TreatmentPlanItem> findByPatientIdOrderByCreatedAtDesc(Long patientId);
}

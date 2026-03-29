package com.odontosuiteadmin.application.service.plantreatment;

import com.odontosuiteadmin.application.dto.plantreatment.CreateTreatmentPlanItemRequest;
import com.odontosuiteadmin.application.dto.plantreatment.TreatmentPlanItemResponse;
import com.odontosuiteadmin.application.dto.plantreatment.UpdateTreatmentPlanItemRequest;
import com.odontosuiteadmin.domain.model.enums.TreatmentStatus;

import java.util.List;

public interface TreatmentPlanService {
    TreatmentPlanItemResponse create(CreateTreatmentPlanItemRequest request);
    List<TreatmentPlanItemResponse> listByPatient(Long patientId);
    TreatmentPlanItemResponse updateStatus(Long id, TreatmentStatus status);
    TreatmentPlanItemResponse update(Long id, UpdateTreatmentPlanItemRequest request);

}

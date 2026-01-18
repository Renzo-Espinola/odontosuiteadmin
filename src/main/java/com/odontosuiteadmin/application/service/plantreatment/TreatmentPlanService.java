package com.odontosuiteadmin.application.service.plantreatment;

import com.odontosuiteadmin.application.dto.plantreatment.CreateTreatmentPlanItemRequest;
import com.odontosuiteadmin.application.dto.plantreatment.TreatmentPlanItemResponse;

import java.util.List;

public interface TreatmentPlanService {
    TreatmentPlanItemResponse create(CreateTreatmentPlanItemRequest request);
    List<TreatmentPlanItemResponse> listByPatient(Long patientId);
}

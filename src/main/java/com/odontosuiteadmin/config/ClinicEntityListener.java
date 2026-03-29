package com.odontosuiteadmin.config;

import com.odontosuiteadmin.domain.model.entity.TenantEntity;
import jakarta.persistence.PrePersist;

public class ClinicEntityListener {

    @PrePersist
    public void prePersist(Object entity) {

        if (!(entity instanceof TenantEntity tenant)) {
            return;
        }

        if (tenant.getClinicId() == null) {
            Long clinicId = TenantContext.getClinicId();

            if (clinicId == null) {
                throw new IllegalStateException(
                        "clinicId no está presente en TenantContext"
                );
            }

            tenant.setClinicId(clinicId);
        }
    }

}

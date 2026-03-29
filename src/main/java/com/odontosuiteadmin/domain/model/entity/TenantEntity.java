package com.odontosuiteadmin.domain.model.entity;

import com.odontosuiteadmin.config.ClinicEntityListener;
import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.ParamDef;

@SuperBuilder
@MappedSuperclass
@FilterDef(name = "clinicFilter", parameters = @ParamDef(name = "clinicId", type = Long.class))
@EntityListeners(ClinicEntityListener.class)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public abstract class TenantEntity {
    @Column(name = "clinic_id", nullable = true)
    private Long clinicId;
}

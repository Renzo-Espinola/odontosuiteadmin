package com.odontosuiteadmin.config;

public class TenantContext {

    private static final ThreadLocal<Long> CLINIC = new ThreadLocal<>();

    public static void setClinicId(Long clinicId) { CLINIC.set(clinicId); }
    public static Long getClinicId() { return CLINIC.get(); }
    public static void clear() { CLINIC.remove(); }

    private TenantContext() {}

}

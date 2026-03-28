package com.odontosuiteadmin.application.dto.security;

import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

@Component
public class CurrentContext {

    public Long userId(Jwt jwt) {
        return Long.valueOf(jwt.getSubject());
    }

    public Long clinicId(Jwt jwt) {
        return jwt.getClaim("activeClinicId");
    }

    public String role(Jwt jwt) {
        return jwt.getClaim("activeRole");
    }

}

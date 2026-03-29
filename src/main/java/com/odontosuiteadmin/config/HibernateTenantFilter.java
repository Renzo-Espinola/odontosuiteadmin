package com.odontosuiteadmin.config;

import jakarta.persistence.EntityManager;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.hibernate.Filter;
import org.hibernate.Session;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
@RequiredArgsConstructor
public class HibernateTenantFilter extends OncePerRequestFilter {

    private final EntityManager entityManager;

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
            throws ServletException, IOException {

        Session session = null;
        Filter enabled = null;

        try {
            Long clinicId = TenantContext.getClinicId();
            if (clinicId != null) {
                session = entityManager.unwrap(Session.class);
                enabled = session.enableFilter("clinicFilter");
                enabled.setParameter("clinicId", clinicId);
            }

            chain.doFilter(req, res);

        } finally {
            if (session != null && enabled != null) {
                try {
                    session.disableFilter("clinicFilter");
                } catch (Exception ignored) {}
            }
        }
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getRequestURI();
        return path.startsWith("/actuator/health");
    }

}

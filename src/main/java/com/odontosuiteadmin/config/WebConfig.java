package com.odontosuiteadmin.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final ClinicFilterInterceptor clinicFilterInterceptor;

    public WebConfig(ClinicFilterInterceptor clinicFilterInterceptor) {
        this.clinicFilterInterceptor = clinicFilterInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(clinicFilterInterceptor)
                .addPathPatterns("/api/**"); // o todos
    }
}

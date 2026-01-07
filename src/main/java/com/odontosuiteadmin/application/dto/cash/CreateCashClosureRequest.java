package com.odontosuiteadmin.application.dto.cash;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

public record CreateCashClosureRequest(
        @NotNull LocalDate date) {
}

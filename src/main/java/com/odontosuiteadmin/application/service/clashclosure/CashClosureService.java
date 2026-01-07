package com.odontosuiteadmin.application.service.clashclosure;

import com.odontosuiteadmin.application.dto.cash.CashClosureResponse;
import com.odontosuiteadmin.application.dto.cash.CreateCashClosureRequest;
import java.time.LocalDate;
import java.util.List;

public interface CashClosureService {
    CashClosureResponse closeDay(CreateCashClosureRequest request, String createdBy);

    CashClosureResponse getByDate(LocalDate date);

    List<CashClosureResponse> list();
}

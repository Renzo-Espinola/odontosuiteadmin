package com.odontosuiteadmin.application.service.money;

import com.odontosuiteadmin.application.dto.cash.CreateMoneyMovementRequest;
import com.odontosuiteadmin.application.dto.cash.MoneyMovementResponse;
import java.time.LocalDateTime;
import java.util.List;

public interface MoneyMovementService {
    MoneyMovementResponse create(CreateMoneyMovementRequest request);

    List<MoneyMovementResponse> list(LocalDateTime from, LocalDateTime to);

    List<MoneyMovementResponse> listByPatient(Long patientId);
}

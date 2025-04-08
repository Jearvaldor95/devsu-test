package com.devsu.account_service.adapter.dto.request;

import com.devsu.account_service.domain.enums.MovementType;

import java.time.LocalDate;

public record MovementRequest(
        LocalDate date,
        MovementType movementType,
        Double value,
        Double balance,
        String movement,
        Integer accountId
) {
}

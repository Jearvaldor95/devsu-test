package com.devsu.account_service.adapter.dto.response;

import com.devsu.account_service.domain.enums.MovementType;

import java.time.LocalDate;

public record MovementResponse(
        LocalDate date,
        String movementType,
        Double value,
        Double balance,
        String movement,
        Integer accountId
) {
}

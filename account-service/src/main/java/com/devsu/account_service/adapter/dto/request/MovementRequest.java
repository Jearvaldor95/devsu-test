package com.devsu.account_service.adapter.dto.request;

import com.devsu.account_service.domain.enums.MovementType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record MovementRequest(
        @NotNull(message = "MomentType is required")
        MovementType movementType,
        @NotNull(message = "Value is required")
        Double value,
        @NotNull(message = "Balance is required")
        Double balance,
        @NotBlank(message = "Movement is required")
        String movement,
        @NotNull(message = "AccountId is required")
        Integer accountId
) {
}

package com.devsu.account_service.adapter.dto.response;

import com.devsu.account_service.domain.enums.MovementType;
import com.devsu.account_service.domain.model.Account;
import com.devsu.account_service.domain.model.Movement;

import java.time.LocalDate;
import java.util.List;

public record MovementResponse(
        LocalDate date,
        String movementType,
        Double value,
        Double balance,
        String movement,
        Integer accountId
) {

    public static MovementResponse of(Movement movement) {
        return new MovementResponse(
                movement.getDate(),
                movement.getMovementType().name(),
                movement.getValue(),
                movement.getBalance(),
                movement.getMovement(),
                movement.getAccountId()
        );
    }

    public static List<MovementResponse> movementResponses(List<Movement> movements) {
        return movements.stream()
                .map(MovementResponse::of)
                .toList();
    }
}

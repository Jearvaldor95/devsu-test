package com.devsu.account_service.domain.repository;

import com.devsu.account_service.domain.model.Movement;

import java.util.List;

public interface MovementRepository {

    Movement saveMovement(Movement movement);

    Movement updateMovement(Integer movementId);

    List<Movement> getMovements();

    List<Movement> findByAccountId(Integer accountId);

}

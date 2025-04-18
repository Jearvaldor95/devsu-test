package com.devsu.account_service.domain.repository;

import com.devsu.account_service.domain.model.Movement;

import java.time.LocalDate;
import java.util.List;

public interface MovementRepository {

    Movement saveMovement(Movement movement);

    Movement updateMovement(Integer movementId, Movement movement);

    List<Movement> getMovements();

    List<Movement> findByAccountId(Integer accountId);

    List<Movement> findMovements(Long accountNumber, LocalDate startDate, LocalDate endDate);

}

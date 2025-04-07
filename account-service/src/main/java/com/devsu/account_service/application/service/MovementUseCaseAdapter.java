package com.devsu.account_service.application.service;

import com.devsu.account_service.domain.model.Movement;
import com.devsu.account_service.domain.repository.MovementRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovementUseCaseAdapter implements MovementUseCase{

    private final MovementRepository movementRepository;

    public MovementUseCaseAdapter(MovementRepository movementRepository){
        this.movementRepository = movementRepository;
    }
    @Override
    public Movement saveMovement(Movement movement) {
        return movementRepository.saveMovement(movement);
    }

    @Override
    public Movement updateMovement(Integer movementId, Movement movement) {
        return movementRepository.updateMovement(movementId, movement);
    }

    @Override
    public List<Movement> getMovements() {
        return movementRepository.getMovements();
    }

    @Override
    public List<Movement> findByAccountId(Integer accountId) {
        return movementRepository.findByAccountId(accountId);
    }
}

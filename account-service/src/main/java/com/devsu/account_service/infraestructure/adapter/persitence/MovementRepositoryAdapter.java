package com.devsu.account_service.infraestructure.adapter.persitence;

import com.devsu.account_service.domain.model.Movement;
import com.devsu.account_service.domain.repository.MovementRepository;
import com.devsu.account_service.exception.MovementNotFoundException;
import com.devsu.account_service.infraestructure.adapter.persitence.entity.MovementEntity;
import com.devsu.account_service.infraestructure.adapter.persitence.mapper.MovementMapper;
import com.devsu.account_service.infraestructure.adapter.persitence.repository.JpaMovementRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public class MovementRepositoryAdapter implements MovementRepository {

    private final JpaMovementRepository jpaMovementRepository;
    private final MovementMapper movementMapper;

    public MovementRepositoryAdapter(JpaMovementRepository jpaMovementRepository, MovementMapper movementMapper){
        this.jpaMovementRepository = jpaMovementRepository;
        this.movementMapper = movementMapper;
    }
    @Override
    public Movement saveMovement(Movement movement) {
        MovementEntity saveMovement = movementMapper.toEntity(movement);
        return movementMapper.toDomain(jpaMovementRepository.save(saveMovement));
    }

    @Override
    public Movement updateMovement(Integer movementId, Movement movement) {
        MovementEntity existingMovement = jpaMovementRepository.findById(movementId)
                .orElseThrow(() -> new MovementNotFoundException("Movement not found with id: " + movementId));

        movementMapper.updateMovement(existingMovement, movement);

        return movementMapper.toDomain(jpaMovementRepository.save(existingMovement));
    }

    @Override
    public List<Movement> getMovements() {
        return movementMapper.toMovements(jpaMovementRepository.findAll());
    }

    @Override
    public List<Movement> findByAccountId(Integer accountId) {
        return movementMapper.toMovements(jpaMovementRepository.findByAccount_AccountId(accountId));
    }

    @Override
    public List<Movement> findMovements(Long accountNumber, LocalDate startDate, LocalDate endDate) {
        List<MovementEntity> movements = jpaMovementRepository.findByAccount_AccountNumberAndDateBetween(accountNumber, startDate, endDate);
        return movementMapper.toMovements(movements);
    }
}

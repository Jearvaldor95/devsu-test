package com.devsu.account_service.infraestructure.adapter.persitence;

import com.devsu.account_service.domain.enums.MovementType;
import com.devsu.account_service.domain.model.Movement;
import com.devsu.account_service.domain.repository.MovementRepository;
import com.devsu.account_service.exception.AccountNotFoundException;
import com.devsu.account_service.exception.FieldInvalidException;
import com.devsu.account_service.exception.MovementNotFoundException;
import com.devsu.account_service.infraestructure.adapter.persitence.entity.AccountEntity;
import com.devsu.account_service.infraestructure.adapter.persitence.entity.MovementEntity;
import com.devsu.account_service.infraestructure.adapter.persitence.mapper.MovementMapper;
import com.devsu.account_service.infraestructure.adapter.persitence.repository.JpaAccountRepository;
import com.devsu.account_service.infraestructure.adapter.persitence.repository.JpaMovementRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public class MovementRepositoryAdapter implements MovementRepository {

    private final JpaMovementRepository jpaMovementRepository;
    private final MovementMapper movementMapper;
    private final JpaAccountRepository jpaAccountRepository;

    public MovementRepositoryAdapter(JpaMovementRepository jpaMovementRepository, MovementMapper movementMapper,
                                     JpaAccountRepository jpaAccountRepository){
        this.jpaMovementRepository = jpaMovementRepository;
        this.movementMapper = movementMapper;
        this.jpaAccountRepository = jpaAccountRepository;
    }

    // Validate balance account
    public void updateBalance(Movement movement){
        // Get the account balance
        Double balance = jpaAccountRepository.findBalanceByAccountId(movement.getAccountId());
        double currentBalance = balance != null ? balance : 0.0;

        // Validate insufficient balance
        if (currentBalance == 0.00 && movement.getMovementType().equals(MovementType.WITHDRAWAL)) {
            throw new FieldInvalidException("The unavailable balance");
        }

        if (movement.getMovementType().equals(MovementType.WITHDRAWAL) && movement.getValue() > currentBalance) {
            throw new FieldInvalidException("Insufficient balance");
        }

        // Get account
        AccountEntity account = jpaAccountRepository.findById(movement.getAccountId())
                .orElseThrow(()-> new AccountNotFoundException("Account not found with id: " + movement.getAccountId()));

        // Validate movement type
        if (movement.getMovementType().equals(MovementType.DEPOSIT)){
            account.setInitialBalance(currentBalance + movement.getValue());
            movement.setBalance(currentBalance + movement.getValue());
            movement.setMovement("Deposito de "+movement.getValue());
        } else if (movement.getMovementType().equals(MovementType.WITHDRAWAL)) {
            account.setInitialBalance(account.getInitialBalance() - movement.getValue());
            movement.setBalance(currentBalance - movement.getValue());
            movement.setMovement("Retiro de "+movement.getValue());
        }
        jpaAccountRepository.save(account);
    }
    @Override
    public Movement saveMovement(Movement movement) {
        updateBalance(movement);
        movement.setDate(LocalDate.now());

        MovementEntity saveMovement = movementMapper.toEntity(movement);
        return movementMapper.toDomain(jpaMovementRepository.save(saveMovement));
    }

    @Override
    public Movement updateMovement(Integer movementId, Movement movement) {
        MovementEntity existingMovement = jpaMovementRepository.findById(movementId)
                .orElseThrow(() -> new MovementNotFoundException("Movement not found with id: " + movementId));

        updateBalance(movement);

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
}

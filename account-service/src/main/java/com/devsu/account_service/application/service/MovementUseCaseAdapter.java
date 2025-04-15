package com.devsu.account_service.application.service;

import com.devsu.account_service.domain.enums.MovementType;
import com.devsu.account_service.domain.model.Account;
import com.devsu.account_service.domain.model.Movement;
import com.devsu.account_service.domain.repository.AccountRepository;
import com.devsu.account_service.domain.repository.MovementRepository;
import com.devsu.account_service.exception.AccountNotFoundException;
import com.devsu.account_service.exception.FieldInvalidException;
import com.devsu.account_service.infraestructure.adapter.persitence.entity.AccountEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class MovementUseCaseAdapter implements MovementUseCase{

    private final MovementRepository movementRepository;
    private final AccountRepository accountRepository;

    public MovementUseCaseAdapter(MovementRepository movementRepository, AccountRepository accountRepository){
        this.movementRepository = movementRepository;
        this.accountRepository = accountRepository;
    }

    // Validate balance account
    public void updateBalance(Movement movement){
        // Get the account balance
        Double balance = accountRepository.findBalanceByAccountId(movement.getAccountId());
        double currentBalance = balance != null ? balance : 0.0;

        // Validate insufficient balance
        if (currentBalance == 0.00 && movement.getMovementType().equals(MovementType.WITHDRAWAL)) {
            throw new FieldInvalidException("The unavailable balance");
        }

        if (movement.getMovementType().equals(MovementType.WITHDRAWAL) && movement.getValue() > currentBalance) {
            throw new FieldInvalidException("Insufficient balance");
        }

        // Get account
        Account account = accountRepository.findByAccountId(movement.getAccountId());

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
        accountRepository.saveAccount(account);
    }

    @Override
    public Movement saveMovement(Movement movement) {
        movement.setDate(LocalDate.now());
        updateBalance(movement);
        return movementRepository.saveMovement(movement);
    }

    @Override
    public Movement updateMovement(Integer movementId, Movement movement) {
        updateBalance(movement);
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

    @Override
    public List<Movement> findByAccount_AccountNumberAndDateBetween(Long accountNumber, LocalDate startDate, LocalDate endDate) {
        return movementRepository.findMovements(accountNumber, startDate, endDate);
    }
}

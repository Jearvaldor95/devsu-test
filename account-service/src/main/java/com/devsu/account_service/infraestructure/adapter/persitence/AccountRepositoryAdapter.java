package com.devsu.account_service.infraestructure.adapter.persitence;

import com.devsu.account_service.domain.model.Account;
import com.devsu.account_service.domain.model.AccountReport;
import com.devsu.account_service.domain.model.Movement;
import com.devsu.account_service.domain.repository.AccountRepository;
import com.devsu.account_service.domain.strategy.AccountStrategyFactory;
import com.devsu.account_service.exception.AccountNotFoundException;
import com.devsu.account_service.exception.ConflictException;
import com.devsu.account_service.infraestructure.adapter.persitence.entity.AccountEntity;
import com.devsu.account_service.infraestructure.adapter.persitence.entity.MovementEntity;
import com.devsu.account_service.infraestructure.adapter.persitence.mapper.AccountMapper;
import com.devsu.account_service.infraestructure.adapter.persitence.mapper.MovementMapper;
import com.devsu.account_service.infraestructure.adapter.persitence.repository.JpaAccountRepository;
import com.devsu.account_service.infraestructure.adapter.persitence.repository.JpaMovementRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public class AccountRepositoryAdapter implements AccountRepository {

    private final JpaAccountRepository jpaAccountRepository;
    private final AccountMapper accountMapper;
    private final AccountStrategyFactory accountStrategyFactory;
    private final JpaMovementRepository jpaMovementRepository;
    private final MovementMapper movementMapper;

    public AccountRepositoryAdapter(JpaAccountRepository jpaAccountRepository, AccountMapper accountMapper, AccountStrategyFactory accountStrategyFactory,
                                    JpaMovementRepository jpaMovementRepository, MovementMapper movementMapper){
        this.jpaAccountRepository = jpaAccountRepository;
        this.accountMapper = accountMapper;
        this.accountStrategyFactory = accountStrategyFactory;
        this.jpaMovementRepository = jpaMovementRepository;
        this.movementMapper = movementMapper;
    }
    @Override
    public Account saveAccount(Account account) {
        // Check the account type
        accountStrategyFactory.getAccountType(account.getAccountType().name());

        // Generate unique account number according to type
        String generatedAccountNumber = accountStrategyFactory.generateAccountNumber(account.getAccountType().name());
        Long accountNumber = Long.valueOf(generatedAccountNumber);
        account.setAccountNumber(accountNumber);

        // Check if the account number already exists
        if (jpaAccountRepository.existsByAccountNumber(accountNumber)) {
            throw new ConflictException("Account already exists");
        }

        AccountEntity saveAccount = accountMapper.toEntity(account);
        return accountMapper.toDomain(jpaAccountRepository.save(saveAccount));
    }

    @Override
    public List<Account> getAccounts() {
        return accountMapper.toAccounts(jpaAccountRepository.findAll());
    }

    @Override
    public Account findByAccountNumber(Long accountNumber) {
        AccountEntity existingAccount = jpaAccountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(()-> new AccountNotFoundException("Account not found with number: " + accountNumber));
        return accountMapper.toDomain(existingAccount);
    }

    @Override
    public Account findByAccountId(Integer accountId) {
        AccountEntity existingAccount = jpaAccountRepository.findById(accountId)
                .orElseThrow(()-> new AccountNotFoundException("Account not found with id:" + accountId));
        return accountMapper.toDomain(existingAccount);
    }

    @Override
    public Account updateAccount(Integer accountId, Account account) {
        AccountEntity existingAccount = jpaAccountRepository.findById(accountId)
                .orElseThrow(()-> new AccountNotFoundException("Account not found with id:" + accountId));

        accountStrategyFactory.getAccountType(account.getAccountType().name());

        accountMapper.updateAccount(existingAccount, account);
        AccountEntity updateAccount = jpaAccountRepository.save(existingAccount);
        return accountMapper.toDomain(updateAccount);
    }

    @Override
    public Account findByCustomerId(Integer customerId) {
        AccountEntity existsAccount = jpaAccountRepository.findByCustomerId(customerId)
                .orElseThrow(()-> new AccountNotFoundException("Account not found for customer"));
        return accountMapper.toDomain(existsAccount);
    }

    @Override
    public AccountReport getReports(LocalDate startDate, LocalDate endDate, Integer customerId) {

        AccountEntity accountEntity = jpaAccountRepository.findByCustomerId(customerId)
                .orElseThrow(() -> new RuntimeException("Account not found for customer"));
        Account account = accountMapper.toDomain(accountEntity);

        List<MovementEntity> movements = jpaMovementRepository
                .findByAccount_AccountNumberAndDateBetween(
                        accountEntity.getAccountNumber(), startDate, endDate
                );
        List<Movement> movement = movementMapper.toMovements(movements);

        return AccountReport.builder()
                .account(account)
                .movements(movement)
                .build();
    }

}

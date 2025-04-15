package com.devsu.account_service.application.service;

import com.devsu.account_service.domain.model.Account;
import com.devsu.account_service.domain.model.AccountReport;
import com.devsu.account_service.domain.model.Movement;
import com.devsu.account_service.domain.repository.AccountRepository;
import com.devsu.account_service.domain.repository.MovementRepository;
import com.devsu.account_service.domain.strategy.AccountStrategy;
import com.devsu.account_service.domain.strategy.AccountStrategyFactory;
import com.devsu.account_service.exception.ConflictException;
import com.devsu.account_service.infraestructure.adapter.external.AccountCreatedEvent;
import com.devsu.account_service.infraestructure.adapter.external.CustomerDto;
import com.devsu.account_service.infraestructure.adapter.messaging.AccountMQProducer;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class AccountUseCaseAdapter implements AccountUseCase{

    private final AccountRepository accountRepository;
    private final AccountStrategyFactory accountStrategyFactory;
    private final AccountMQProducer accountMQProducer;
    private final MovementRepository movementRepository;

    public AccountUseCaseAdapter(AccountRepository accountRepository, AccountStrategyFactory accountStrategyFactory, AccountMQProducer accountMQProducer,
                                 MovementRepository movementRepository){
        this.accountRepository = accountRepository;
        this.accountStrategyFactory = accountStrategyFactory;
        this.accountMQProducer = accountMQProducer;
        this.movementRepository = movementRepository;

    }

    // validate account type
    public void validateAccountType(Account account){
        String accountType = account.getAccountType().name();

        // Check the account type
        AccountStrategy strategy = accountStrategyFactory.getAccountType(accountType);
        account = strategy.createAccount(account);

        // Generate unique account number according to type
        String generatedAccountNumber = accountStrategyFactory.generateAccountNumber(account.getAccountType().name());
        Long accountNumber = Long.valueOf(generatedAccountNumber);
        account.setAccountNumber(accountNumber);

    }

    @Override
    public Account saveAccount(Account account) {

        accountRepository.getCustomerById(account.getCustomerId());

        validateAccountType(account);

        if (accountRepository.existsAccountNumber(account.getAccountNumber())){
            throw new ConflictException("Account already exists");
        }

        // Notify customer service
        accountMQProducer.publishAccountCreated(
                new AccountCreatedEvent(account.getCustomerId(), account.getAccountNumber(), account.getAccountType().name())
        );

        return accountRepository.saveAccount(account);
    }

    @Override
    public List<Account> getAccounts() {
        return accountRepository.getAccounts();
    }

    @Override
    public Account findByAccountNumber(Long accountNumber) {
        return accountRepository.findByAccountNumber(accountNumber);
    }

    @Override
    public Account findByAccountId(Integer accountId) {
        return accountRepository.findByAccountId(accountId);
    }

    @Override
    public Account updateAccount(Integer accountId, Account account) {
        accountRepository.getCustomerById(account.getCustomerId());
        accountStrategyFactory.getAccountType(account.getAccountType().name());
        return accountRepository.updateAccount(accountId, account);
    }

    @Override
    public List<Account> findByCustomerId(Integer customerId) {
        return accountRepository.findByCustomerId(customerId);
    }

    @Override
    public List<AccountReport> getReports(LocalDate startDate, LocalDate endDate, Integer customerId) {
        CustomerDto customerDto = accountRepository.getCustomerById(customerId);
        List<Account> accountEntities = accountRepository.findByCustomerId(customerDto.getId());

        List<AccountReport> reports = new ArrayList<>();

        for (Account account : accountEntities) {
            List<Movement> movements = movementRepository
                    .findMovements(
                            account.getAccountNumber(),
                            startDate,
                            endDate
                    );

            for (Movement movement : movements) {
                AccountReport report = AccountReport.builder()
                        .date(movement.getDate())
                        .customerName(customerDto.getName())
                        .accountNumber(account.getAccountNumber())
                        .accountType(account.getAccountType().name())
                        .initialBalance(account.getInitialBalance())
                        .status(account.getStatus())
                        .movement(movement.getValue())
                        .availableBalance(movement.getBalance())
                        .build();

                reports.add(report);
            }
        }

        return reports;
    }
}

package com.devsu.account_service.infraestructure.adapter.persitence;

import com.devsu.account_service.domain.model.Account;
import com.devsu.account_service.domain.model.AccountReport;
import com.devsu.account_service.domain.repository.AccountRepository;
import com.devsu.account_service.domain.strategy.AccountStrategy;
import com.devsu.account_service.domain.strategy.AccountStrategyFactory;
import com.devsu.account_service.exception.AccountNotFoundException;
import com.devsu.account_service.exception.ConflictException;
import com.devsu.account_service.infraestructure.adapter.external.CustomerDto;
import com.devsu.account_service.infraestructure.adapter.external.exception.CustomerNotFoundException;
import com.devsu.account_service.infraestructure.adapter.persitence.entity.AccountEntity;
import com.devsu.account_service.infraestructure.adapter.persitence.entity.MovementEntity;
import com.devsu.account_service.infraestructure.adapter.persitence.mapper.AccountMapper;
import com.devsu.account_service.infraestructure.adapter.persitence.mapper.MovementMapper;
import com.devsu.account_service.infraestructure.adapter.persitence.repository.JpaAccountRepository;
import com.devsu.account_service.infraestructure.adapter.persitence.repository.JpaMovementRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Repository
public class AccountRepositoryAdapter implements AccountRepository {

    private final JpaAccountRepository jpaAccountRepository;
    private final AccountMapper accountMapper;
    private final AccountStrategyFactory accountStrategyFactory;
    private final JpaMovementRepository jpaMovementRepository;
    private final WebClient webClient;

    public AccountRepositoryAdapter(JpaAccountRepository jpaAccountRepository, AccountMapper accountMapper, AccountStrategyFactory accountStrategyFactory,
                                    JpaMovementRepository jpaMovementRepository, MovementMapper movementMapper, WebClient webClient){
        this.jpaAccountRepository = jpaAccountRepository;
        this.accountMapper = accountMapper;
        this.accountStrategyFactory = accountStrategyFactory;
        this.jpaMovementRepository = jpaMovementRepository;
        this.webClient = webClient;
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

        // Check if the account number already exists
        if (jpaAccountRepository.existsByAccountNumber(accountNumber)) {
            throw new ConflictException("Account already exists");
        }
    }

    // Validate if the customer exists
    public CustomerDto getCustomer(Integer customerId){
        try {
            return webClient.get()
                    .uri("/customers/{customerId}", customerId)
                    .retrieve()
                    .bodyToMono(CustomerDto.class)
                    .block();
        }catch (Exception exception){
            throw new CustomerNotFoundException("Customer not found with id: "+ customerId);
        }

    }

    @Override
    public Account saveAccount(Account account) {
        getCustomer(account.getCustomerId());

        validateAccountType(account);

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

        return accountMapper.toDomain(jpaAccountRepository.save(existingAccount));
    }

    @Override
    public List<Account> findByCustomerId(Integer customerId) {
        List<AccountEntity> accountEntities = jpaAccountRepository.findByCustomerId(customerId);
        return accountMapper.toAccounts(accountEntities);
    }

    @Override
    public List<AccountReport> getReports(LocalDate startDate, LocalDate endDate, Integer customerId) {
        CustomerDto customerDto = getCustomer(customerId);
        List<AccountEntity> accountEntities = jpaAccountRepository.findByCustomerId(customerId);

        List<AccountReport> reports = new ArrayList<>();

        for (AccountEntity accountEntity : accountEntities) {
            List<MovementEntity> movements = jpaMovementRepository
                    .findByAccount_AccountNumberAndDateBetween(
                            accountEntity.getAccountNumber(),
                            startDate,
                            endDate
                    );

            for (MovementEntity movement : movements) {
                AccountReport report = AccountReport.builder()
                        .date(movement.getDate())
                        .customerName(customerDto.getName())
                        .accountNumber(accountEntity.getAccountNumber())
                        .accountType(accountEntity.getAccountType().name())
                        .initialBalance(accountEntity.getInitialBalance())
                        .status(accountEntity.getStatus())
                        .movement(movement.getValue())
                        .availableBalance(movement.getBalance())
                        .build();

                reports.add(report);
            }
        }

        return reports;
    }

}

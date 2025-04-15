package com.devsu.account_service.infraestructure.adapter.persitence;

import com.devsu.account_service.domain.model.Account;
import com.devsu.account_service.domain.repository.AccountRepository;
import com.devsu.account_service.exception.AccountNotFoundException;
import com.devsu.account_service.infraestructure.adapter.external.CustomerDto;
import com.devsu.account_service.infraestructure.adapter.external.exception.CustomerNotFoundException;
import com.devsu.account_service.infraestructure.adapter.persitence.entity.AccountEntity;
import com.devsu.account_service.infraestructure.adapter.persitence.mapper.AccountMapper;
import com.devsu.account_service.infraestructure.adapter.persitence.repository.JpaAccountRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Repository
public class AccountRepositoryAdapter implements AccountRepository {

    private final JpaAccountRepository jpaAccountRepository;
    private final AccountMapper accountMapper;
    private final WebClient webClient;

    public AccountRepositoryAdapter(JpaAccountRepository jpaAccountRepository, AccountMapper accountMapper,
                                    WebClient webClient){
        this.jpaAccountRepository = jpaAccountRepository;
        this.accountMapper = accountMapper;
        this.webClient = webClient;
    }

    // Validate if the customer exists
    @Override
    public CustomerDto getCustomerById(Integer customerId){
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

        accountMapper.updateAccount(existingAccount, account);

        return accountMapper.toDomain(jpaAccountRepository.save(existingAccount));
    }

    @Override
    public List<Account> findByCustomerId(Integer customerId) {
        List<AccountEntity> accountEntities = jpaAccountRepository.findByCustomerId(customerId);
        return accountMapper.toAccounts(accountEntities);
    }

    @Override
    public Boolean existsAccountNumber(Long accountNumber) {
        return jpaAccountRepository.existsByAccountNumber(accountNumber);
    }

    @Override
    public Double findBalanceByAccountId(Integer accountId) {
        return jpaAccountRepository.findBalanceByAccountId(accountId);
    }

}

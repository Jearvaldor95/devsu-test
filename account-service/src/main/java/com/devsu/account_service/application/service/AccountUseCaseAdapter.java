package com.devsu.account_service.application.service;

import com.devsu.account_service.domain.model.Account;
import com.devsu.account_service.domain.model.AccountReport;
import com.devsu.account_service.domain.repository.AccountRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class AccountUseCaseAdapter implements AccountUseCase{

    private final AccountRepository accountRepository;

    public AccountUseCaseAdapter(AccountRepository accountRepository){
        this.accountRepository = accountRepository;
    }
    @Override
    public Account saveAccount(Account account) {
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
        return accountRepository.updateAccount(accountId, account);
    }

    @Override
    public List<Account> findByCustomerId(Integer customerId) {
        return accountRepository.findByCustomerId(customerId);
    }

    @Override
    public List<AccountReport> getReports(LocalDate startDate, LocalDate endDate, Integer customerId) {
        return accountRepository.getReports(startDate, endDate, customerId);
    }
}

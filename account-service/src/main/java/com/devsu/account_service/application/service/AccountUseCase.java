package com.devsu.account_service.application.service;

import com.devsu.account_service.domain.model.Account;
import com.devsu.account_service.domain.model.AccountReport;

import java.time.LocalDate;
import java.util.List;

public interface AccountUseCase {
    Account saveAccount(Account account);

    List<Account> getAccounts();

    Account findByAccountNumber(Long accountNumber);

    Account findByAccountId(Integer accountId);

    Account updateAccount(Integer accountId, Account account);

    Account findByCustomerId(Integer customerId);

    AccountReport getReports(LocalDate startDate, LocalDate endDate, Integer customerId);
}

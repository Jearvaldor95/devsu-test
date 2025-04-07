package com.devsu.account_service.domain.repository;

import com.devsu.account_service.domain.model.Account;

import java.time.LocalDate;
import java.util.List;

public interface AccountRepository {

    Account saveAccount(Account account);

    List<Account> getAccounts();

    Account findByAccountNumber(Long accountNumber);

    Account findByAccountId(Integer accountId);

    Account updateAccount(Integer accountId);

    Account findByCustomerId(Integer customerId);

    Account getReports(LocalDate starDate, LocalDate andDate, Integer customerId);
}

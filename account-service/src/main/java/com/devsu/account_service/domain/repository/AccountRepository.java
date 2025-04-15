package com.devsu.account_service.domain.repository;

import com.devsu.account_service.domain.model.Account;
import com.devsu.account_service.infraestructure.adapter.external.CustomerDto;

import java.util.List;

public interface AccountRepository {

    Account saveAccount(Account account);

    CustomerDto getCustomerById(Integer customerId);

    List<Account> getAccounts();

    Account findByAccountNumber(Long accountNumber);

    Account findByAccountId(Integer accountId);

    Account updateAccount(Integer accountId, Account account);

    List<Account> findByCustomerId(Integer customerId);

    Boolean existsAccountNumber(Long accountNumber);

    Double findBalanceByAccountId(Integer accountId);
}

package com.devsu.account_service.domain.strategy;

import com.devsu.account_service.domain.model.Account;

public interface AccountStrategy {

    Account createAccount(Account account);

}

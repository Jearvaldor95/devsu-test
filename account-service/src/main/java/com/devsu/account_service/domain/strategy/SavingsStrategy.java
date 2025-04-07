package com.devsu.account_service.domain.strategy;

import com.devsu.account_service.domain.enums.AccountType;
import com.devsu.account_service.domain.model.Account;
import com.devsu.account_service.exception.FieldInvalidException;
import org.springframework.stereotype.Component;

@Component("SAVINGS_ACCOUNT")
public class SavingsStrategy implements AccountStrategy{
    @Override
    public Account createAccount(Account account) {
        account.setAccountType(AccountType.SAVINGS_ACCOUNT);
        account.setInitialBalance(account.getInitialBalance());
        return account;
    }
}

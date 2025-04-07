package com.devsu.account_service.domain.strategy;

import com.devsu.account_service.domain.enums.AccountType;
import com.devsu.account_service.domain.model.Account;
import com.devsu.account_service.exception.FieldInvalidException;
import org.springframework.stereotype.Component;

@Component("CURRENT_ACCOUNT")
public class CurrentStrategy implements AccountStrategy{
    @Override
    public Account createAccount(Account account) {
        if (account.getInitialBalance() < 25000.00){
            throw new FieldInvalidException("Invalid initial balance");
        }
        account.setAccountType(AccountType.SAVINGS_ACCOUNT);
        account.setInitialBalance(account.getInitialBalance());
        return account;
    }
}

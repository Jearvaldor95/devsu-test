package com.devsu.account_service.adapter.dto.response;

import com.devsu.account_service.domain.enums.AccountType;
import com.devsu.account_service.domain.model.Account;
import com.devsu.account_service.infraestructure.adapter.persitence.entity.AccountEntity;

import java.util.ArrayList;
import java.util.List;

public record AccountResponse(
        Long accountNumber,
        String accountType,
        Double initialBalance,
        Boolean status,
        Integer customerId
) {

    public static AccountResponse of(Account account) {
        return new AccountResponse(
                account.getAccountNumber(),
                account.getAccountType().name(),
                account.getInitialBalance(),
                account.getStatus(),
                account.getCustomerId()
        );
    }


}

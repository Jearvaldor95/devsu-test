package com.devsu.account_service.domain.strategy;

import com.devsu.account_service.domain.enums.AccountType;
import com.devsu.account_service.exception.FieldInvalidException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Random;

@Component
public class AccountStrategyFactory {

    @Autowired
    private final Map<String, AccountStrategy> strategyMap;

    public AccountStrategyFactory(Map<String, AccountStrategy> strategyMap) {
        this.strategyMap = strategyMap;
    }

    public AccountStrategy getAccountType(String accountType) {
        AccountStrategy accountStrategy = strategyMap.get(accountType);
        if (accountStrategy == null) {
            throw new FieldInvalidException("Account type not supported: " + accountType);
        }
        return accountStrategy;
    }

    public String generateAccountNumber(String accountType) {
        String prefix = switch (accountType.toUpperCase()) {
            case "SAVINGS_ACCOUNT" -> "48";
            case "CURRENT_ACCOUNT" -> "49";
            default -> "00";
        };
        String randomPart = String.valueOf(1000 + new Random().nextInt(9000));
        return prefix + randomPart;
    }



}

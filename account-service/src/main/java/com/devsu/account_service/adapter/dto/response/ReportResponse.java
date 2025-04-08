package com.devsu.account_service.adapter.dto.response;

import com.devsu.account_service.domain.enums.AccountType;

import java.time.LocalDate;

public record ReportResponse(
        LocalDate date,
        String customer,
        Long accountNumber,
        AccountType accountType,
        Double initialBalance,
        Boolean status,
        Boolean movement,
        Double availableBalance
) {
}

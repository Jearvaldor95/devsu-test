package com.devsu.account_service.adapter.dto.response;

import com.devsu.account_service.domain.enums.AccountType;
import com.devsu.account_service.domain.model.Account;
import com.devsu.account_service.domain.model.AccountReport;
import com.devsu.account_service.domain.model.Movement;

import java.time.LocalDate;
import java.util.List;

public record ReportResponse(
        LocalDate date,
        String customerName,
        Long accountNumber,
        String accountType,
        Double initialBalance,
        Boolean status,
        Double movement,
        Double availableBalance
) {

    public static ReportResponse of(AccountReport accountReport) {
        return new ReportResponse(
                accountReport.getDate(),
                accountReport.getCustomerName(),
                accountReport.getAccountNumber(),
                accountReport.getAccountType(),
                accountReport.getInitialBalance(),
                accountReport.getStatus(),
                accountReport.getMovement(),
                accountReport.getAvailableBalance()
        );
    }

    public static List<ReportResponse> reportResponses(List<AccountReport> reports) {
        return reports.stream()
                .map(ReportResponse::of)
                .toList();
    }

}

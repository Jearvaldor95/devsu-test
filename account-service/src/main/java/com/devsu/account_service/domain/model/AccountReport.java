package com.devsu.account_service.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AccountReport {
    private LocalDate date;
    private String customerName;
    private Long accountNumber;
    private String accountType;
    private Double initialBalance;
    private Boolean status;
    private Double movement;
    private Double availableBalance;
}

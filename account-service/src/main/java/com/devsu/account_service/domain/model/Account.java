package com.devsu.account_service.domain.model;

import com.devsu.account_service.domain.enums.AccountType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Account {

    private Integer accountId;
    private Long accountNumber;
    private AccountType accountType;
    private Double initialBalance;
    private Boolean status;
    private Integer pin;
    private Integer customerId;
}

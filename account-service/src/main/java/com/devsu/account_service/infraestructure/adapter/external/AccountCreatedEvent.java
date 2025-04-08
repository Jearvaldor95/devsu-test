package com.devsu.account_service.infraestructure.adapter.external;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AccountCreatedEvent {

    private Integer customerId;
    private Long accountNumber;
    private String accountType;
}

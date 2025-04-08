package com.devsu.customer_service.infraestruture.adapter.messaging;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountCreatedEvent {

    private Integer customerId;
    private Long accountNumber;
    private String accountType;
}

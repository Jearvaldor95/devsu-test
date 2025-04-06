package com.devsu.customer_service.adapter.dto;

import com.devsu.customer_service.domain.model.Customer;

import java.util.List;

public record CustomerResponseList(
        List<CustomerResponse> customerResponses
) {
    public static CustomerResponseList of(List<Customer> customers) {
        return new CustomerResponseList(
                customers.stream()
                        .map(CustomerResponse::of)
                        .toList()
        );
    }
}

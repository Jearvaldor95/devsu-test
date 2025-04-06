package com.devsu.customer_service.adapter.dto;

import com.devsu.customer_service.domain.model.Customer;

public record CustomerResponse(
        Integer id,
        String name,
        int age,
        Integer identification,
        String address,
        String phone,
        Boolean status
){
    public static CustomerResponse of(Customer customer) {
        return new CustomerResponse(
                customer.getPersonId(),
                customer.getName(),
                customer.getAge(),
                customer.getIdentification(),
                customer.getAddress(),
                customer.getPhone(),
                customer.getStatus()
                );
    }
}

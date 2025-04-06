package com.devsu.customer_service.adapter.dto;

import com.devsu.customer_service.domain.model.Customer;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CustomerRequest(
    @NotBlank(message = "Name is required")
    String name,
    String gender,
    int age,
    @NotNull(message = "Identification is required")
    Integer identification,
    @NotBlank(message = "address is required")
    String address,
    @NotBlank(message = "Phone is required")
    String phone,
    @NotBlank(message = "password is required")
    String password,
    @NotNull(message = "Status is required")
    Boolean status
){}

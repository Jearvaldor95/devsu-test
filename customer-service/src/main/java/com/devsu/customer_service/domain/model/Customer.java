package com.devsu.customer_service.domain.model;

import lombok.Data;

@Data
public class Customer extends Person{
    private String password;
    private String status;
    private String packageKey;
}

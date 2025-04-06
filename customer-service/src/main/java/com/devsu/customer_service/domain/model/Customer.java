package com.devsu.customer_service.domain.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class Customer extends Person{
    private String password;
    private Boolean status;
}

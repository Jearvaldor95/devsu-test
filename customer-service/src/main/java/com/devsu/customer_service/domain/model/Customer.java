package com.devsu.customer_service.domain.model;

import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Customer extends Person{
    private String password;
    private Boolean status;

    @Builder(buildMethodName = "customerBuilder")
    public Customer(Integer personId, String name, String gender, int age, Integer identification, String address, String phone, String password, Boolean status) {
        super(personId, name, gender, age, identification, address, phone);
        this.password = password;
        this.status = status;
    }
}

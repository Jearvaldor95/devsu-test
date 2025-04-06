package com.devsu.customer_service.domain.model;

import lombok.Data;

@Data
public class Person {
    private Integer personId;
    private String name;
    private String gender;
    private int age;
    private Integer identification;
    private String address;
    private String phone;
}

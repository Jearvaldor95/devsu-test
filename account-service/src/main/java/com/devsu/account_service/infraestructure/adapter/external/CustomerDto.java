package com.devsu.account_service.infraestructure.adapter.external;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomerDto {
    private Integer id;
    private String name;
    private int age;
    private Integer identification;
    private String address;
    private String phone;
    private Boolean status;
}

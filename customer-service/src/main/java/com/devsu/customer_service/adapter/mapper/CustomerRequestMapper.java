package com.devsu.customer_service.adapter.mapper;

import com.devsu.customer_service.adapter.dto.CustomerRequest;
import com.devsu.customer_service.domain.model.Customer;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CustomerRequestMapper {
    Customer toCustomer(CustomerRequest request);
}

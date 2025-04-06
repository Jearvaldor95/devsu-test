package com.devsu.customer_service.infraestruture.adapter.persitence.mapper;

import com.devsu.customer_service.domain.model.Customer;
import com.devsu.customer_service.infraestruture.adapter.persitence.entity.CustomerEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CustomerMapper {

    @Mappings({
            @Mapping(target = "customerId", source = "personId")
    })
    CustomerEntity toCustomerEntity(Customer customer);
    @Mappings({
            @Mapping(target = "personId", source = "customerId")
    })
    Customer toCustomer(CustomerEntity customerEntity);

    List<Customer> toCustomers(List<CustomerEntity> customerEntities);

    void updateCustomer(@MappingTarget CustomerEntity customerEntity, Customer customer);
}

package com.devsu.customer_service.domain.repository;

import com.devsu.customer_service.domain.model.Customer;

import java.util.List;

public interface CustomerRepository {

    Customer saveCustomer(Customer customer);

    List<Customer> getCustomers();

    Customer findById(Integer customerId);

    Customer updateCustomer(Integer customerId, Customer customer);

    void deleteCustomer(Integer customerId);

    Boolean existsByIdentification(Integer identification);
}

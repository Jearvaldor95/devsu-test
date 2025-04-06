package com.devsu.customer_service.application.service;

import com.devsu.customer_service.domain.model.Customer;

import java.util.List;

public interface CustomerUseCase {

    Customer saveCustomer(Customer customer);

    List<Customer> getCustomers();

    Customer findById(Integer customerId);

    Customer updateCustomer(Integer customerId, Customer customer);

    void deleteCustomer(Integer customerId);
}

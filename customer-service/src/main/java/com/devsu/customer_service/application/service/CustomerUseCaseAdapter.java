package com.devsu.customer_service.application.service;

import com.devsu.customer_service.domain.model.Customer;
import com.devsu.customer_service.domain.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerUseCaseAdapter implements CustomerUseCase{

    private final CustomerRepository customerRepository;

    public CustomerUseCaseAdapter(CustomerRepository customerRepository){
        this.customerRepository = customerRepository;
    }
    @Override
    public Customer saveCustomer(Customer customer) {
        customerRepository.existsByIdentification(customer.getIdentification());
        return customerRepository.saveCustomer(customer);
    }

    @Override
    public List<Customer> getCustomers() {
        return customerRepository.getCustomers();
    }

    @Override
    public Customer findById(Integer customerId) {
        return customerRepository.findById(customerId);
    }

    @Override
    public Customer updateCustomer(Integer customerId, Customer customer) {
        return customerRepository.updateCustomer(customerId, customer);
    }

    @Override
    public void deleteCustomer(Integer customerId) {
        customerRepository.deleteCustomer(customerId);
    }
}

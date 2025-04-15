package com.devsu.customer_service.infraestruture.adapter.persitence;

import com.devsu.customer_service.domain.model.Customer;
import com.devsu.customer_service.domain.repository.CustomerRepository;
import com.devsu.customer_service.exception.CustomerAlreadyExistsException;
import com.devsu.customer_service.exception.CustomerNotFoundException;
import com.devsu.customer_service.infraestruture.adapter.persitence.entity.CustomerEntity;
import com.devsu.customer_service.infraestruture.adapter.persitence.mapper.CustomerMapper;
import com.devsu.customer_service.infraestruture.adapter.persitence.repository.JpaCustomerRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public class CustomerRepositoryAdapter implements CustomerRepository {

    private final JpaCustomerRepository jpaCustomerRepository;
    private final CustomerMapper customerMapper;

    public CustomerRepositoryAdapter(JpaCustomerRepository jpaCustomerRepository, CustomerMapper customerMapper){
        this.jpaCustomerRepository = jpaCustomerRepository;
        this.customerMapper = customerMapper;
    }
    @Override
    public Customer saveCustomer(Customer customer) {
        CustomerEntity entity = customerMapper.toCustomerEntity(customer);
        CustomerEntity savedEntity = jpaCustomerRepository.save(entity);
        return customerMapper.toCustomer(savedEntity);
    }


    @Override
    public List<Customer> getCustomers() {
        return customerMapper.toCustomers(jpaCustomerRepository.findAll());
    }

    @Override
    public Customer findById(Integer customerId) {
        CustomerEntity existingCustomer = jpaCustomerRepository.findById(customerId)
                .orElseThrow(() -> new CustomerNotFoundException("Customer not found with id: " + customerId));
        return customerMapper.toCustomer(existingCustomer);
    }

    @Override
    public Customer updateCustomer(Integer customerId, Customer customer) {
        CustomerEntity existingCustomer = jpaCustomerRepository.findById(customerId)
                .orElseThrow(() -> new CustomerNotFoundException("Customer not found with id: " + customerId));

        customerMapper.updateCustomer(existingCustomer, customer);

        return customerMapper.toCustomer(jpaCustomerRepository.save(existingCustomer));
    }


    @Override
    public void deleteCustomer(Integer customerId) {
        CustomerEntity existingCustomer = jpaCustomerRepository.findById(customerId)
                .orElseThrow(() -> new CustomerNotFoundException("Customer not found with id: " + customerId));
        jpaCustomerRepository.delete(existingCustomer);

    }

    @Override
    public Boolean existsByIdentification(Integer identification) {
        boolean exists = jpaCustomerRepository.existsByIdentification(identification);
        if (exists) {
            throw new CustomerAlreadyExistsException("Customer with this identification already exists");
        }
        return false;
    }
}

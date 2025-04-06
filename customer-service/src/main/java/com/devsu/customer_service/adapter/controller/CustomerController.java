package com.devsu.customer_service.adapter.controller;

import com.devsu.customer_service.adapter.dto.CustomerRequest;
import com.devsu.customer_service.adapter.dto.CustomerResponse;
import com.devsu.customer_service.adapter.dto.CustomerResponseList;
import com.devsu.customer_service.adapter.mapper.CustomerRequestMapper;
import com.devsu.customer_service.application.service.CustomerUseCase;
import com.devsu.customer_service.domain.model.Customer;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/customers")
@Tag(name = "Customer Controller")
public class CustomerController {

    private final CustomerUseCase customerUseCase;
    private final CustomerRequestMapper customerRequestMapper;

    public CustomerController(CustomerUseCase customerUseCase, CustomerRequestMapper customerRequestMapper){
        this.customerUseCase = customerUseCase;
        this.customerRequestMapper = customerRequestMapper;
    }

    @PostMapping
    @Operation(summary = "Save customer")
    public ResponseEntity<CustomerResponse> saveCustomer(@Valid @RequestBody CustomerRequest customerRequest){
        Customer customer = customerRequestMapper.toCustomer(customerRequest);
        CustomerResponse response = CustomerResponse.of(customerUseCase.saveCustomer(customer));

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get all customers")
    public CustomerResponseList getCustomers(){
        return CustomerResponseList.of(customerUseCase.getCustomers());
    }

    @GetMapping("/{customerId}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get customer by ID")
    public CustomerResponse getCustomer(@PathVariable Integer customerId){
        return CustomerResponse.of(customerUseCase.findById(customerId));
    }

    @PutMapping("/{customerId}")
    @Operation(summary = "Update customer")
    public ResponseEntity<CustomerResponse> updateCustomer(@PathVariable Integer customerId, @Valid @RequestBody CustomerRequest customerRequest){
        Customer customer = customerRequestMapper.toCustomer(customerRequest);
        CustomerResponse response = CustomerResponse.of(customerUseCase.updateCustomer(customerId, customer));
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/{customerId}")
    @Operation(summary = "Delete customer")
    public ResponseEntity<?> deleteCustomer(@PathVariable Integer customerId){
        customerUseCase.deleteCustomer(customerId);
        return new ResponseEntity<>("Customer deleted!", HttpStatus.OK);
    }
}

package com.devsu.customer_service.infraestruture.adapter.persitence.repository;

import com.devsu.customer_service.infraestruture.adapter.persitence.entity.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaCustomerRepository extends JpaRepository<CustomerEntity, Integer> {

    boolean existsByIdentification(Integer identification);
}

package com.devsu.account_service.infraestructure.adapter.persitence.repository;

import com.devsu.account_service.infraestructure.adapter.persitence.entity.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.Optional;

public interface JpaAccountRepository extends JpaRepository<AccountEntity, Integer> {

    Boolean existsByAccountNumber(Long accountNumber);

    Optional<AccountEntity> findByAccountNumber(Long accountNumber);

    Optional<AccountEntity> findByCustomerId(Integer customerId);

    @Query("SELECT a.initialBalance FROM AccountEntity a WHERE a.accountId = :accountId")
    Double findBalanceByAccountId(Integer accountId);


}

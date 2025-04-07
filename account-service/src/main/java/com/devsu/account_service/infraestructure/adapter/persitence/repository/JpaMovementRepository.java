package com.devsu.account_service.infraestructure.adapter.persitence.repository;

import com.devsu.account_service.infraestructure.adapter.persitence.entity.MovementEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface JpaMovementRepository extends JpaRepository<MovementEntity, Integer> {

    List<MovementEntity> findByAccount_AccountNumberAndDateBetween(Long accountNumber, LocalDate startDate, LocalDate endDate);

    List<MovementEntity> findByAccount_AccountId(Integer accountId);




}

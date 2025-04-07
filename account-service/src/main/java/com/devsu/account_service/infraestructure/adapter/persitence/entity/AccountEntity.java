package com.devsu.account_service.infraestructure.adapter.persitence.entity;

import com.devsu.account_service.domain.enums.AccountType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "accounts")
public class AccountEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer accountId;
    @Column(nullable = false, unique = true)
    private Long accountNumber;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AccountType accountType;
    @Column(nullable = false)
    private Double initialBalance;
    @Column(nullable = false)
    private Boolean status;
    @Column(nullable = false)
    private Integer pin;
    @Column(nullable = false)
    private Integer customerId;
    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL)
    private List<MovementEntity> movements;
}

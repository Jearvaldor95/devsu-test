package com.devsu.account_service.infraestructure.adapter.persitence.entity;

import com.devsu.account_service.domain.enums.MovementType;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "movements")
public class MovementEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer movementId;
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate date = LocalDate.now();
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MovementType movementType;
    @Column(nullable = false)
    private Double value;
    @Column(nullable = false)
    private Double balance;
    @Column(nullable = false)
    private String movement;
    @ManyToOne
    @JoinColumn(name = "accountId", nullable = false)
    private AccountEntity account;
}

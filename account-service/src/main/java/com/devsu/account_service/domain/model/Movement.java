package com.devsu.account_service.domain.model;

import com.devsu.account_service.domain.enums.MovementType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Movement {
    private Integer movementId;
    private LocalDate date;
    private MovementType movementType;
    private Double value;
    private Double balance;
    private String movement;
    private Integer accountId;
}

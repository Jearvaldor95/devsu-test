package com.devsu.account_service.adapter.dto.request;

import com.devsu.account_service.domain.enums.AccountType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record AccountRequest(

        AccountType accountType,
        @Schema(example = "00.00")
        Double initialBalance,
        @NotNull(message = "Status is required")
        Boolean status,
        @NotNull(message = "Pin is required")
        Integer pin,
        @NotNull(message = "CustomerId is required")
        Integer customerId
) {

}

package com.devsu.account_service.adapter.controller;

import com.devsu.account_service.adapter.dto.request.AccountRequest;
import com.devsu.account_service.adapter.dto.response.AccountResponse;
import com.devsu.account_service.adapter.mapper.AccountRequestMapper;
import com.devsu.account_service.application.service.AccountUseCase;
import com.devsu.account_service.domain.model.Account;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/accounts")
@Tag(name = "Account Service")
public class AccountController {

    private final AccountUseCase accountUseCase;
    private final AccountRequestMapper accountRequestMapper;

    public AccountController(AccountUseCase accountUseCase, AccountRequestMapper accountRequestMapper){
        this.accountUseCase = accountUseCase;
        this.accountRequestMapper = accountRequestMapper;
    }

    @PostMapping
    @Operation(summary = "Save Account")
    public ResponseEntity<AccountResponse> saveAccount(@Valid @RequestBody AccountRequest accountRequest){
        Account account = accountRequestMapper.toAccount(accountRequest);
        AccountResponse response = AccountResponse.of(accountUseCase.saveAccount(account));
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }


}

package com.devsu.account_service.adapter.controller;

import com.devsu.account_service.adapter.dto.request.AccountRequest;
import com.devsu.account_service.adapter.dto.response.AccountResponse;
import com.devsu.account_service.adapter.dto.response.ReportResponse;
import com.devsu.account_service.adapter.mapper.AccountRequestMapper;
import com.devsu.account_service.application.service.AccountUseCase;
import com.devsu.account_service.domain.model.Account;
import com.devsu.account_service.domain.model.AccountReport;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/accounts")
@Tag(name = "Account Controller")
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

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get all accounts")
    public ResponseEntity<List<AccountResponse>> getAccounts(){
        List<AccountResponse> accountResponses = AccountResponse.accountResponses(accountUseCase.getAccounts());
        return ResponseEntity.ok(accountResponses);
    }

    @GetMapping("/number/{number}")
    @Operation(summary = "Get account by accountNumber")
    public ResponseEntity<AccountResponse> findByAccountNumber(@PathVariable Long number){
        AccountResponse response = AccountResponse.of(accountUseCase.findByAccountNumber(number));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{accountId}")
    @Operation(summary = "Get account by accountId")
    public ResponseEntity<AccountResponse> findByAccountId(@PathVariable Integer accountId){
        AccountResponse response = AccountResponse.of(accountUseCase.findByAccountId(accountId));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/{accountId}")
    @Operation(summary = "Update account")
    public ResponseEntity<AccountResponse> updateCustomer(@PathVariable Integer accountId, @Valid @RequestBody AccountRequest accountRequest){
        Account account = accountRequestMapper.toAccount(accountRequest);
        AccountResponse response = AccountResponse.of(accountUseCase.updateAccount(accountId, account));
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/customer/{customerId}")
    @Operation(summary = "Get accounts by customerId")
    public ResponseEntity<List<AccountResponse>> findByCustomerId(@PathVariable Integer customerId){
        List<AccountResponse> response = AccountResponse.accountResponses(accountUseCase.findByCustomerId(customerId));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/reports")
    @Operation(summary = "Get reports account by dates and customerId")
    public ResponseEntity<List<ReportResponse>> getReports(@RequestParam LocalDate startDate, @RequestParam LocalDate endDate, @RequestParam Integer customerId){
        List<ReportResponse> response = ReportResponse.reportResponses(accountUseCase.getReports(startDate, endDate, customerId));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }



}

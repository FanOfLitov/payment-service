package com.example.payment.controller;

import com.example.payment.dto.AccountDashboardDto;
import com.example.payment.dto.CreateAccountRequest;
import com.example.payment.dto.DepositRequest;
import com.example.payment.entity.Account;
import com.example.payment.service.AccountService;
import org.springframework.web.bind.annotation.*;
import com.example.payment.dto.AccountDto;

import java.util.List;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping
    public List<Account> getAllAccounts() {
        return accountService.getAllAccounts();
    }

    @GetMapping("/user/{userId}")
    public List<Account> getAccountsByUser(@PathVariable Long userId) {
        return accountService.getAccountsByUser(userId);
    }

    @PostMapping
    public Account createAccount(@RequestBody CreateAccountRequest request) {
        return accountService.createAccount(
                request.userId(),
                request.accountNumber(),
                request.currency()
        );
    }
    @PostMapping("/{accountId}/deposit")
    public Account deposit(
            @PathVariable Long accountId,
            @RequestBody DepositRequest request
    ) {
        return accountService.deposit(
                accountId,
                request.amount()
        );
    }

    @GetMapping("/view")
    public List<AccountDto> getAccountView(){
        return accountService.getAllAccountDtos();
    }

    @GetMapping("/{accountId}/dashboard")
    public AccountDashboardDto getDashboard(
            @PathVariable Long accountId
    ) {
        return accountService.getDashboard(accountId);
    }
}
package com.example.payment.service;

import com.example.payment.entity.Account;
import com.example.payment.entity.User;
import com.example.payment.repository.AccountRepository;
import com.example.payment.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class AccountService {

    private final AccountRepository accountRepository;
    private final UserRepository userRepository;

    public AccountService(
            AccountRepository accountRepository,
            UserRepository userRepository
    ) {
        this.accountRepository = accountRepository;
        this.userRepository = userRepository;
    }

    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }

    public List<Account> getAccountsByUser(Long userId) {
        return accountRepository.findByUserId(userId);
    }

    public Account createAccount(
            Long userId,
            String accountNumber,
            String currency
    ) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Account account = new Account(
                user,
                accountNumber,
                BigDecimal.ZERO,
                currency
        );

        return accountRepository.save(account);
    }
}
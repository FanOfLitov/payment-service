package com.example.payment.service;

import com.example.payment.dto.AccountDto;
import com.example.payment.entity.Account;
import com.example.payment.entity.User;
import com.example.payment.repository.AccountRepository;
import com.example.payment.repository.TransactionAnalyticsRepository;
import com.example.payment.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import com.example.payment.repository.TransactionAnalyticsRepository;
import com.example.payment.dto.AccountDashboardDto;


import java.math.BigDecimal;
import java.util.List;

@Service
public class AccountService {

    private final AccountRepository accountRepository;
    private final UserRepository userRepository;

    private final TransactionAnalyticsRepository transactionAnalyticsRepository;

    public AccountService(
            AccountRepository accountRepository,
            UserRepository userRepository,
            TransactionAnalyticsRepository transactionAnalyticsRepository
    ) {
        this.accountRepository = accountRepository;
        this.userRepository = userRepository;
        this.transactionAnalyticsRepository = transactionAnalyticsRepository;
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


    @Transactional
    public Account deposit(Long accountId, BigDecimal amount) {

        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new RuntimeException("Amount must be greater than zero");
        }

        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new RuntimeException("Account not found"));

        account.setBalance(
                account.getBalance().add(amount)
        );

        return accountRepository.save(account);
    }

    public List<AccountDto> getAllAccountDtos(){
        return accountRepository.findAll()
                .stream()
                .map(account -> new AccountDto(
                        account.getId(),
                        account.getUser().getId(),
                        account.getUser().getName(),
                        account.getAccountNumber(),
                        account.getBalance(),
                        account.getCurrency(),
                        account.getCreatedAt()
                        )

                ).toList();
    }

    public AccountDashboardDto getDashboard(Long accountId) {

        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new RuntimeException("Account not found"));

        long transactionCount =
                transactionAnalyticsRepository.countTransactions(accountId);

        BigDecimal outgoingTotal =
                transactionAnalyticsRepository.sumOutgoing(accountId);

        BigDecimal incomingTotal =
                transactionAnalyticsRepository.sumIncoming(accountId);

        BigDecimal maxTransaction =
                transactionAnalyticsRepository.maxTransaction(accountId);

        return new AccountDashboardDto(
                account.getId(),
                account.getUser().getName(),
                account.getAccountNumber(),
                account.getBalance(),
                account.getCurrency(),
                transactionCount,
                outgoingTotal,
                incomingTotal,
                maxTransaction
        );
    }

}
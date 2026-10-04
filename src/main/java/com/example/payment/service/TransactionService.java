package com.example.payment.service;

import com.example.payment.dto.AccountAnalyticsDto;
import com.example.payment.entity.Account;
import com.example.payment.entity.Transaction;
import com.example.payment.repository.AccountRepository;
import com.example.payment.repository.TransactionAnalyticsRepository;
import com.example.payment.repository.TransactionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import  com.example.payment.dto.TransactionDetailsDto;

import java.math.BigDecimal;
import java.util.List;

@Service
public class TransactionService {
    private final TransactionAnalyticsRepository transactionAnalyticsRepository;

    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;

    public TransactionService(
            AccountRepository accountRepository,
            TransactionRepository transactionRepository,
            TransactionAnalyticsRepository transactionAnalyticsRepository
    ) {
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
        this.transactionAnalyticsRepository = transactionAnalyticsRepository;
    }

    @Transactional
    public Transaction transfer(
            Long fromAccountId,
            Long toAccountId,
            BigDecimal amount
    ) {
        if (fromAccountId.equals(toAccountId)) {
            throw new RuntimeException("Cannot transfer money to the same account");
        }

        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new RuntimeException("Amount must be greater than zero");
        }

        Account fromAccount = accountRepository.findById(fromAccountId)
                .orElseThrow(() -> new RuntimeException("Source account not found"));

        Account toAccount = accountRepository.findById(toAccountId)
                .orElseThrow(() -> new RuntimeException("Target account not found"));

        if (fromAccount.getBalance().compareTo(amount) < 0) {
            throw new RuntimeException("Insufficient funds");
        }

        fromAccount.setBalance(
                fromAccount.getBalance().subtract(amount)
        );

        toAccount.setBalance(
                toAccount.getBalance().add(amount)
        );

        accountRepository.save(fromAccount);
        accountRepository.save(toAccount);

        Transaction transaction = new Transaction(
                fromAccount,
                toAccount,
                amount,
                "COMPLETED"
        );

        return transactionRepository.save(transaction);
    }

    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }

    public List<Transaction> getOutgoingTransactions(Long accountId) {
        return transactionRepository.findByFromAccountId(accountId);
    }

    public List<Transaction> getIncomingTransactions(Long accountId) {
        return transactionRepository.findByToAccountId(accountId);
    }

    public List<Transaction> getAccountHistory(Long accountId){
        return transactionRepository.findHistoryByAccountId(accountId);
    }

    public long getTransactionCount(Long accountId){
        return transactionRepository.countTransactionByAccountId(accountId);
    }

    public BigDecimal getOutgoingTotal(Long accountId){
        return transactionRepository.sumOutgoingByAccountId(accountId);

    }
    public List<TransactionDetailsDto> getTransactionDetails() {
        return transactionAnalyticsRepository.findAllTransactionDetails();
    }

    public AccountAnalyticsDto getAccountAnalytics(Long accountId) {
        long count =
                transactionAnalyticsRepository.countTransactions(accountId);

        BigDecimal outgoing =
                transactionAnalyticsRepository.sumOutgoing(accountId);

        BigDecimal incoming =
                transactionAnalyticsRepository.sumIncoming(accountId);

        BigDecimal max =
                transactionAnalyticsRepository.maxTransaction(accountId);

        return new AccountAnalyticsDto(
                accountId,
                count,
                outgoing,
                incoming,
                max
        );
    }




}
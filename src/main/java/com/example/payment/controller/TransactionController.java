package com.example.payment.controller;
import com.example.payment.dto.AccountAnalyticsDto;
import com.example.payment.dto.TransferRequest;
import com.example.payment.entity.Transaction;
import com.example.payment.service.TransactionService;
import org.springframework.web.bind.annotation.*;
import com.example.payment.dto.TransactionDetailsDto;
import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping("/transfer")
    public Transaction transfer(@RequestBody TransferRequest request) {
        return transactionService.transfer(
                request.fromAccountId(),
                request.toAccountId(),
                request.amount()
        );
    }

    @GetMapping
    public List<Transaction> getAllTransactions() {
        return transactionService.getAllTransactions();
    }

    @GetMapping("/outgoing/{accountId}")
    public List<Transaction> getOutgoingTransactions(
            @PathVariable Long accountId
    ) {
        return transactionService.getOutgoingTransactions(accountId);
    }

    @GetMapping("/incoming/{accountId}")
    public List<Transaction> getIncomingTransactions(
            @PathVariable Long accountId
    ) {
        return transactionService.getIncomingTransactions(accountId);
    }

    @GetMapping("/history/{accountId}")
    public List<Transaction> getAccountHistory(
            @PathVariable Long accountId
    ){
        return transactionService.getAccountHistory(accountId);

    }

    @GetMapping("/count/{accountId}")
    public long getTransactionCount(@PathVariable Long accountId){
        return transactionService.getTransactionCount(accountId);
    }

    @GetMapping("/outgoing-total/{accountId}")
    public BigDecimal getOutgoingTotal(@PathVariable Long accountId){
        return transactionService.getOutgoingTotal(accountId);
    }

    @GetMapping("/details")
    public List<TransactionDetailsDto> getTransactionDetails() {
        return transactionService.getTransactionDetails();
    }

    @GetMapping("/analytics/{accountId}")
    public AccountAnalyticsDto getAccountAnalytics(
            @PathVariable Long accountId
    ) {
        return transactionService.getAccountAnalytics(accountId);
    }
}
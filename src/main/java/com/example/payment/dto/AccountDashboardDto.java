package com.example.payment.dto;

import java.math.BigDecimal;

public record AccountDashboardDto(
        Long accountId,
        String owner,
        String accountNumber,
        BigDecimal balance,
        String currency,
        long transactionCount,
        BigDecimal outgoingTotal,
        BigDecimal incomingTotal,
        BigDecimal maxTransaction
) {
}

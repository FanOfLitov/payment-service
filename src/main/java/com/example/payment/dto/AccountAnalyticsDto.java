package com.example.payment.dto;

import java.math.BigDecimal;

public record AccountAnalyticsDto(
        Long accountId,
        long transactionCount,
        BigDecimal outgoingTotal,
        BigDecimal incomingTotal,
        BigDecimal maxTransaction
) {
}

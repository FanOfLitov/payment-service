package com.example.payment.dto;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public record AccountDto(
        Long id,
        Long userId,
        String userName,
        String accountNumber,
        BigDecimal balance,
        String currency,
        LocalDateTime createdAt
) {
}
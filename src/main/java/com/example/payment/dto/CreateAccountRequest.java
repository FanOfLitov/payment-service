package com.example.payment.dto;

public record CreateAccountRequest(
        Long userId,
        String accountNumber,
        String currency
) {
}

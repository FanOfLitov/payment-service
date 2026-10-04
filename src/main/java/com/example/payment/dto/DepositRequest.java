package com.example.payment.dto;

import java.math.BigDecimal;

public record DepositRequest(
        BigDecimal amount
) {
}
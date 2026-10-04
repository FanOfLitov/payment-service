package com.example.payment.dto;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public record TransactionDetailsDto(
    Long transactionId,
    String fromUser,
    String fromAccount,
    String toUser,
    String toAccount,
    BigDecimal amount,
    String status,
    LocalDateTime createdAt){
}

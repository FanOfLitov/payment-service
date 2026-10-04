package com.example.payment.dto;

import java.math.BigDecimal;

public record TransferRequest (
        Long fromAccountId,
        Long toAccountId,
        BigDecimal amount
){

}

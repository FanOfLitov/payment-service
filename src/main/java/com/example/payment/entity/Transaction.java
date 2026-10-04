package com.example.payment.entity;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name="transactions")
public class Transaction {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="from_account_id", nullable = false)
    private Account fromAccount;

    @ManyToOne
    @JoinColumn(name ="to_account_id", nullable = false)
    private Account toAccount;

    @Column(nullable = false, precision=19, scale =2)
    private BigDecimal amount;

    @Column(nullable=false, length=20)
    private String status;

    @Column(name="created_at", nullable=false)
    private LocalDateTime createdAt;

    public Transaction() {
    }
    public Transaction(
            Account fromAccount,
            Account toAccount,
            BigDecimal amount,
            String status
    ){
        this.fromAccount =fromAccount;
        this.toAccount = toAccount;
        this.amount=amount;
        this.status=status;
        this.createdAt=LocalDateTime.now();
    }

    public Long getId(){
        return id;
    }

    public Account getFromAccount(){
        return fromAccount;
    }

    public BigDecimal getAmount(){
        return amount;
    }

    public String getStatus(){
        return status;
    }

    public LocalDateTime getCreatedAt(){
        return createdAt;
    }




}

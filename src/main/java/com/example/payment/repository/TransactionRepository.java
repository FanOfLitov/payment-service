package com.example.payment.repository;

import com.example.payment.entity.Transaction;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.util.List;

public interface TransactionRepository  extends JpaRepository<Transaction, Long>{
    List<Transaction> findByFromAccountId(Long accountId);

    List<Transaction> findByToAccountId(Long accountId);


    @Query(value = """
        SELECT t.*
        FROM transactions t
        WHERE t.from_account_id=:accountId
        OR t.to_account_id=:accountId
        ORDER BY t.created_at DESC
        """, nativeQuery = true)
    List<Transaction>findHistoryByAccountId(Long accountId);


    @Query(value= """
        select count(*)
        from transactions
        where from_account_id=:accountId
        or to_Account_id = :accountId
               

    """, nativeQuery =true)
    long countTransactionByAccountId(Long accountId);


    @Query(value= """
        select coalesce(sum(amount), 0)
        from transactions
        where from_account_id=:accountId
                
        
        """, nativeQuery=true)
    BigDecimal sumOutgoingByAccountId(Long accountId);

}

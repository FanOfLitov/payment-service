package com.example.payment.repository;
import com.example.payment.dto.TransactionDetailsDto;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public class TransactionAnalyticsRepository {
    private final JdbcTemplate jdbcTemplate;

    public TransactionAnalyticsRepository(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }


    public List<TransactionDetailsDto> findAllTransactionDetails(){
        String sql= """
                select
                    t.id as transaction_id,
                    u_from.name as from_user,
                    a_from.account_number as from_account,
                    u_to.name as to_user,
                    a_to.account_number as to_account,
                    t.amount,
                    t.status,
                    t.created_at
                from transactions t
                join accounts a_from
                    on t.from_account_id = a_from.id
                join users u_from
                    on a_from.user_id=u_from.id
                join accounts a_to
                    on t.to_account_id=a_to.id
                join users u_to
                    on a_to.user_id=u_to.id
                order by t.created_at desc
               
                """;

        return jdbcTemplate.query(
                sql,
                (resultSet, rowNum) ->new TransactionDetailsDto(
                        resultSet.getLong("transaction_id"),
                        resultSet.getString("from_user"),
                        resultSet.getString("from_account"),
                        resultSet.getString("to_user"),
                        resultSet.getString("to_account"),
                        resultSet.getBigDecimal("amount"),
                        resultSet.getString("status"),
                        resultSet.getTimestamp("created_at").toLocalDateTime()

                )
        );
    }


    public long countTransactions(Long accountId) {
        String sql = """
            SELECT COUNT(*)
            FROM transactions
            WHERE from_account_id = ?
               OR to_account_id = ?
            """;

        Long result = jdbcTemplate.queryForObject(
                sql,
                Long.class,
                accountId,
                accountId
        );

        return result;
    }

    public BigDecimal sumOutgoing(Long accountId) {
        String sql = """
            SELECT COALESCE(SUM(amount), 0)
            FROM transactions
            WHERE from_account_id = ?
            """;

        return jdbcTemplate.queryForObject(
                sql,
                BigDecimal.class,
                accountId
        );
    }

    public BigDecimal sumIncoming(Long accountId){
        String sql= """
                select coalesce(sum(amount), 0)
                from transactions
                where to_account_id = ?""";

        return jdbcTemplate.queryForObject(
                sql,
                BigDecimal.class,
                accountId
        );
    }

    public BigDecimal maxTransaction(Long accountId){
        String sql= """
                select coalesce(max(amount),0)
                from transactions
                where from_account_id =?
                or to_account_id=?""";

        return jdbcTemplate.queryForObject(
                sql,
                BigDecimal.class,
                accountId,
                accountId
        );
    }
}

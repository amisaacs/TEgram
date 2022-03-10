package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlRowSetResultSetExtractor;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
public class JdbcTransferDao implements TransferDao {
    private JdbcTemplate jdbcTemplate;
    public JdbcTransferDao(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate =  jdbcTemplate;

    }
// need to make sure it's auth user

    @Override
    public void makeTransfer(Transfer transfer, BigDecimal balance) {
        //        should be able to choose from a list of users to send TE Bucks to.
        String sql = "BEGIN TRANSACTION; " +
                " UPDATE account SET balance = balance - ? " +
                " WHERE user_id = ?" +
                " UPDATE account SET balance = balance + ? " +
                " WHERE user_id = ? " +
                " COMMIT;";
        if (transfer.getAccountFrom() != transfer.getAccountTo() && transfer.getAmount()){

        }
        jdbcTemplate.update(sql, transfer.getAmount(), transfer.getAccountFrom(),
                transfer.getAmount(), transfer.getAccountTo());


//        I can't send more TE Bucks than I have in my account.
//        I can't send a zero or negative amount.
//        A Sending Transfer has an initial status of Approved.
    }

}

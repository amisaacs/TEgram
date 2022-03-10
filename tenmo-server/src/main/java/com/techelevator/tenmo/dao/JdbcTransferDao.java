package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlRowSetResultSetExtractor;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import java.math.BigDecimal;
import java.util.List;

public class JdbcTransferDao implements TransferDao {
    private JdbcTemplate jdbcTemplate;
    public JdbcTransferDao(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate =  jdbcTemplate;

    }
// need to make sure it's auth user

    @Override
    public void makeTransfer(Long senderId, Long recipientId, BigDecimal transferAmount) {
        //        should be able to choose from a list of users to send TE Bucks to.
        List<User> recipientList;
        String sql = "BEGIN TRANSACTION;" +
                "UPDATE account SET balance = balance - ?" +
                "WHERE user_id = ?" +
                "UPDATE account SET balance = balance + ?" +
                "WHERE user_id = ?" +
                "COMMIT;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, senderId);
        while (results.next()){
            User user = mapRowToUser();
        }




//                I must not be allowed to send money to myself.
//                A transfer includes the User IDs of the from and to users and the amount of TE Bucks.
//                The receiver's account balance is increased by the amount of the transfer.
//        The sender's account balance is decreased by the amount of the transfer.
//        I can't send more TE Bucks than I have in my account.
//        I can't send a zero or negative amount.
//        A Sending Transfer has an initial status of Approved.
    }

}

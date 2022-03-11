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
        //if (transfer.getAccountFrom() != transfer.getAccountTo() && transfer.getAmount()){

        }
        //jdbcTemplate.update(sql, transfer.getAmount(), transfer.getAccountFrom(),
         //       transfer.getAmount(), transfer.getAccountTo());


//        I can't send more TE Bucks than I have in my account.
//        I can't send a zero or negative amount.
//        A Sending Transfer has an initial status of Approved.


    //insert a new transfer into the transfer table
    public Transfer createTransfer(Transfer newTransfer){
        String sql = "INSERT INTO transfer (transfer_type_id, transfer_status_id, account_from, " +
                "account_to, amount) " +
                "VALUES ( ?,?,?,?, ?) RETURNING transfer_id";

        Long newId = jdbcTemplate.queryForObject(sql, Long.class, newTransfer.getTransferTypeId()
                , newTransfer.getAccountFrom(), newTransfer.getAccountTo(),
                newTransfer.getAmount());

        return getTransfer(newId);
    }

    public Transfer getTransfer(Long id){
        Transfer transfer = null;
        String sql = "SELECT transfer_type_id , transfer_status_id, account_from, account_to, " +
                "amount " +
                " FROM transfer WHERE transfer_id = ?";
        SqlRowSet result = jdbcTemplate.queryForRowSet(sql, id);

        if( result.next()){
            transfer = mapRowToTransfer(result);
        }
        return transfer;
    }

    private Transfer mapRowToTransfer(SqlRowSet result){
        Transfer transfer = new Transfer();
        transfer.setId(result.getLong("transfer_id"));
        transfer.setTransferTypeId(result.getLong("transfer_type_id"));
        transfer.setTransferStatusId(result.getLong("transfer_status_id"));
        transfer.setAccountFrom(result.getLong("account_from"));
        transfer.setAccountTo(result.getLong("account_to"));
        transfer.setAmount(result.getBigDecimal("amount"));
        return transfer;
    }

}

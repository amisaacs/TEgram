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
    private static final long TRANSFER_STATUS_PENDING = 1L;
    private static final long TRANSFER_STATUS_APPROVED = 2L;
    private static final long TRANSFER_STATUS_REJECTED = 3L;
    public JdbcTransferDao(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate =  jdbcTemplate;

    }

// need to make sure it's auth user
@Override
    public void makeTransfer (Transfer transfer, BigDecimal senderBalance){

        long status = TRANSFER_STATUS_PENDING;
        try{
            if (updateAccount(transfer, senderBalance)){
                status = TRANSFER_STATUS_APPROVED;
            }else{
                status = TRANSFER_STATUS_REJECTED;
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }finally {
            transfer.setTransferStatusId(status);
            createTransfer(transfer);
        }

    }


    private boolean updateAccount(Transfer transfer, BigDecimal senderBalance) {
        boolean isSuccess = false;
        //        should be able to choose from a list of users to send TE Bucks to.
        String sql =  " UPDATE account SET balance = balance - ? " +
                " WHERE user_id = ?" +
                " UPDATE account SET balance = balance + ? " +
                " WHERE user_id = ? ";

        // checking sender cannot transfer to themselves, transfer amount >0, transfer amount<= senderBalance
        if (transfer.getAccountFrom() != transfer.getAccountTo()
                && (transfer.getAmount().compareTo(senderBalance) != 1)
                && (transfer.getAmount().compareTo(new BigDecimal(0)) == 1)) {
            jdbcTemplate.update(sql, transfer.getAmount(), transfer.getAccountFrom(),
                    transfer.getAmount(), transfer.getAccountTo());
            isSuccess = true;

        }
        return isSuccess;
    }


    //insert a new transfer into the transfer table
    private Transfer createTransfer(Transfer newTransfer){
        String sql = "INSERT INTO transfer (transfer_type_id, transfer_status_id, account_from, " +
                "account_to, amount) " +
                "VALUES ( ?,?,?,?,?) RETURNING transfer_id";

        Long newId = jdbcTemplate.queryForObject(sql, Long.class, newTransfer.getTransferTypeId()
                , newTransfer.getTransferStatusId(),newTransfer.getAccountFrom(), newTransfer.getAccountTo(),
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

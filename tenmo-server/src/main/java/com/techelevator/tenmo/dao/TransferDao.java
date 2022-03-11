package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Transfer;

import java.math.BigDecimal;

public interface TransferDao {
    boolean updateAccount(Transfer transfer, BigDecimal balance);
}

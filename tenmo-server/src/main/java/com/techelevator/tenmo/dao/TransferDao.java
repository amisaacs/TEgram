package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Transfer;

import java.math.BigDecimal;

public interface TransferDao {
    void makeTransfer(Transfer transfer, BigDecimal balance);
}

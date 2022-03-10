package com.techelevator.tenmo.dao;

import java.math.BigDecimal;

public interface TransferDao {
    void makeTransfer(Long senderId, Long recipientId, BigDecimal transferAmount);
}

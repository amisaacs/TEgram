package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Transfer;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.List;

public interface TransferDao {
    void makeTransfer (Transfer transfer, BigDecimal senderBalance);

    List<Transfer> getTransferByUserId(long id);

    List<Transfer> getTransfers();

    Transfer getTransfer(Long transferId);
}

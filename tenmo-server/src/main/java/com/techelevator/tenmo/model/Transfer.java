package com.techelevator.tenmo.model;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

public class Transfer {
    private long id;
//    @NotNull(message = "Transfer type cannot be blank.")
//    @Min( value = 1)
    private long transferTypeId;
//    @NotNull (message = "Transfer status cannot be blank.")

    private long transferStatusId = 2L; // transfer status default is approved
//    @NotNull (message = "Sender cannot be blank.")
//    @Min( value = 1)
    private long accountFrom;
//    @NotNull (message = "Recipient cannot be blank.")
//    @Min( value = 1)
    private long accountTo;
//    @Positive (message = "Zero or negative amount not accepted.")
    @Min( value = 1)
    private BigDecimal amount;
    public Transfer() {}


    public Transfer(long transferTypeId, long transferStatusId,
                    long accountFrom, long accountTo, BigDecimal amount) {

        this.transferTypeId = transferTypeId;
        this.transferStatusId = transferStatusId;
        this.accountFrom = accountFrom;
        this.accountTo = accountTo;
        this.amount = amount;
    }



    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getTransferTypeId() {
        return transferTypeId;
    }

    public void setTransferTypeId(long transferTypeId) {
        this.transferTypeId = transferTypeId;
    }

    public long getTransferStatusId() {
        return transferStatusId;
    }

    public void setTransferStatusId(long transferStatusId) {
        this.transferStatusId = transferStatusId;
    }

    public long getAccountFrom() {
        return accountFrom;
    }

    public void setAccountFrom(long accountFrom) {
        this.accountFrom = accountFrom;
    }

    public long getAccountTo() {
        return accountTo;
    }

    public void setAccountTo(long accountTo) {
        this.accountTo = accountTo;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "Transfer{" +
                "id=" + id +
                ", transferTypeId=" + transferTypeId +
                ", transferStatusId=" + transferStatusId +
                ", accountFrom=" + accountFrom +
                ", accountTo=" + accountTo +
                ", amount=" + amount +
                '}';
    }
}

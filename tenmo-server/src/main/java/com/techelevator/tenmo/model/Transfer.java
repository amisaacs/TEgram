package com.techelevator.tenmo.model;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

public class Transfer {
    private Long id;
    @NotBlank (message = "Transfer type cannot be blank.")
    private Long transferTypeId;
    @NotBlank (message = "Transfer status cannot be blank.")
    private Long transerStatusId = 2L;
    @NotBlank (message = "Sender cannot be blank.")
    private Long accountFrom;
    @NotBlank (message = "Recipient cannot be blank.")
    private Long accountTo;
    @Positive (message = "Zero or negative amount not accepted.")
    private BigDecimal amount;

    public Transfer(Long id, Long transferTypeId, Long transerStatusId,
                    Long accountFrom, Long accountTo, BigDecimal amount) {

        this.id = id;
        this.transferTypeId = transferTypeId;
        this.transerStatusId = transerStatusId;
        this.accountFrom = accountFrom;
        this.accountTo = accountTo;
        this.amount = amount;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTransferTypeId() {
        return transferTypeId;
    }

    public void setTransferTypeId(Long transferTypeId) {
        this.transferTypeId = transferTypeId;
    }

    public Long getTranserStatusId() {
        return transerStatusId;
    }

    public void setTranserStatusId(Long transerStatusId) {
        this.transerStatusId = transerStatusId;
    }

    public Long getAccountFrom() {
        return accountFrom;
    }

    public void setAccountFrom(Long accountFrom) {
        this.accountFrom = accountFrom;
    }

    public Long getAccountTo() {
        return accountTo;
    }

    public void setAccountTo(Long accountTo) {
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
                ", transerStatusId=" + transerStatusId +
                ", accountFrom=" + accountFrom +
                ", accountTo=" + accountTo +
                ", amount=" + amount +
                '}';
    }
}

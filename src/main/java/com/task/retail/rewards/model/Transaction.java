package com.task.retail.rewards.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * Customer Transaction - Each record of a transaction.
 * @author prasannaks
 */
public class Transaction {

    private Long customerId;
    private String transactionId;
    private Double amount;
    private LocalDate transactionDate;

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public LocalDate getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(LocalDate transactionDate) {
        this.transactionDate = transactionDate;
    }
}

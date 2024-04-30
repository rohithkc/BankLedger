package dev.codescreen.models;

import java.util.Date;

// Class to define Transaction - Load, Authorization
// Includes getter and setter methods for data processing

public class Transaction{

    private String transactionId;
    private String accountId;
    private double amount;
    private EventType eventType;
    private Date timestamp;

    public Transaction(String transactionId, String accountId, double amount, Date timestamp, EventType eventType) {
        this.transactionId = transactionId;
        this.accountId = accountId;
        this.amount = amount;
        this.timestamp = timestamp;
        this.eventType = eventType;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public EventType getEventType() {
        return eventType;
    }

    public void setEventType(EventType eventType) {
        this.eventType = eventType;
    }

    @Override
    public String toString() {
        return "Transaction{" +
               "transactionId='" + transactionId + '\'' +
               ", accountId='" + accountId + '\'' +
               ", amount=" + amount +
               ", timestamp=" + timestamp +
               ", eventType=" + eventType +
               '}';
    }
}

package dev.codescreen.models;

import java.time.LocalDateTime;

// Class to define Transaction - Load, Authorization
// Includes getter and setter methods for data processing

public class Transaction{

    private String transactionId;
    private String accountId;
    private double amount;
    private TransactionType type;
    private LocalDateTime timestamp;

    // Define types of transactions
    public enum TransactionType {
        LOAD, AUTHORIZATION
    }


    // Constructor
    public Transaction(String transactionId, String accountId, double amount, TransactionType type, LocalDateTime timestamp) {
        this.transactionId = transactionId;
        this.accountId = accountId;
        this.amount = amount;
        this.type = type;
        this.timestamp = timestamp;
    }

    // Getter and Setter methods
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

    public TransactionType getType() {
        return type;
    }

    public void setType(TransactionType type) {
        this.type = type;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }



}
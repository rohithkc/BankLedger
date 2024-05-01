package dev.codescreen.models;

/*
* Class to define user account
* Includes getter and setter methods for data processing
*/

public class Account {
    private String accountId;
    private double balance;

    public Account(String accountId, double initialBalance) {
        this.accountId = accountId;
        this.balance = initialBalance;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public void deposit(double amount) {
        this.balance += amount;
    }

    public void withdraw(double amount) {
        this.balance -= amount;
    }

    @Override
    public String toString() {
        return "Account{" +
               "accountId='" + accountId + '\'' +
               ", balance=" + balance +
               '}';
    }
}
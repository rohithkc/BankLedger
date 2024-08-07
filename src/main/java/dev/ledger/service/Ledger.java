package dev.ledger.service;
import org.springframework.stereotype.Service;
import dev.ledger.models.Transaction;
import dev.ledger.models.EventType;
import dev.ledger.repository.TransactionRepository;
import java.util.Date;
import java.util.List;

/*
 * This class is intended to handle all Business logic:
 * Depositing funds, withdrawing funds, and getting balance
 */

 @Service
public class Ledger {

    private final TransactionRepository transactionRepository;

    public Ledger(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public double deposit(String accountId, double amount) {
        // Generate a unique transaction ID
        String uid = java.util.UUID.randomUUID().toString();
        Transaction transaction = new Transaction(
            uid, 
            accountId,
            amount, //positive amount for deposit
            new Date(), // Current date and time
            EventType.LOAD
        );
        transactionRepository.save(transaction);
        return getCurrentBalance(accountId);
    }

    public double withdraw(String accountId, double amount) {
        // Check if there are sufficient funds
        if (getCurrentBalance(accountId) < amount) {
            throw new IllegalArgumentException("Insufficient funds");
        }else{
            // Generate a unique transaction ID
            String uid = java.util.UUID.randomUUID().toString();
            Transaction transaction = new Transaction(
                uid, 
                accountId,
                -amount, // Negative amount for withdrawal
                new Date(), // Current date and time
                EventType.AUTHORIZATION
            );
            transactionRepository.save(transaction);
            return getCurrentBalance(accountId);
        }
    }

    public double getCurrentBalance(String accountId) {
        List<Transaction> transactions = transactionRepository.findByAccountId(accountId);
        double balance = 0.0;

        // Loop through each transaction and add the transaction amount to the balance
        for (Transaction transaction : transactions) {
            balance += transaction.getAmount();
        }

        return balance;
    }
}
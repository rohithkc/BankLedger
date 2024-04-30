package dev.codescreen.repository;

import model.Transaction;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/*
 * This class is meant to keep a track of all the various transactions that have occured for each user. 
 */
public class TransactionRepository {

    // Initialize Hashmap
    private final ConcurrentHashMap<String, List<Transaction>> transactionsMap = new ConcurrentHashMap<>();

    public void save(Transaction transaction) {
        // Retrieve user accountId
        String accountId = transaction.getAccountId();
        List<Transaction> user_transactions = transactionsMap.get(accountId);

        // Check if user transactions is even initialized in the map
        if (user_transactions == null) {
            user_transactions = new ArrayList<>();
            transactionsMap.put(accountId, user_transactions);
        }
        
        // Add transaction to the user
        user_transactions.add(transaction);
    }

    public List<Transaction> findAll() {
        List<Transaction> allTransactions = new ArrayList<>();
        for (List<Transaction> transactions : transactionsMap.values()) {
            allTransactions.addAll(transactions);
        }
        return allTransactions;
    }

    public List<Transaction> findByAccountId(String accountId) {

        // Get the list of transactions for the account.
        List<Transaction> user_transactions = transactionsMap.get(accountId);

        // If no transactions are found, return an empty list.
        if (transactions == null) {
            return Collections.emptyList();
        }

        // Return a copy of the list to avoid external modifications.
        return new ArrayList<>(transactions);  
    }

}
package dev.codescreen.repository;

import model.Transaction;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class TransactionRepository {

    private final ConcurrentHashMap<String, List<Transaction>> transactionsMap = new ConcurrentHashMap<>();

    public void save(Transaction transaction) {
        String accountId = transaction.getAccountId();
        List<Transaction> cur_transactions = transactionsMap.get(accountId);

        if (cur_transactions == null) {
            cur_transactions = new ArrayList<>();
            transactionsMap.put(accountId, cur_transactions);
        }
        
        cur_transactions.add(transaction);
    }

    public List<Transaction> findAll() {
        List<Transaction> allTransactions = new ArrayList<>();
        for (List<Transaction> transactions : transactionsMap.values()) {
            allTransactions.addAll(transactions);
        }
        return allTransactions;
    }

}
package dev.codescreen.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import dev.codescreen.service.Ledger;


/*
 * This class acts as the controller to handle incoming HTTP requests, mapping them to the appropriate service methods and endpoints
 */

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {
    private final Ledger ledgerService;

    public TransactionController(Ledger ledgerService) {
        this.ledgerService = ledgerService;
    }

    @PutMapping("/deposit/{accountId}")
    public ResponseEntity<Double> deposit(@PathVariable String accountId, @RequestParam double amount) {
        if (amount <= 0) {
            return ResponseEntity.badRequest().body(null); // Respond with 400 Bad Request for non-positive amounts
        }
        double newBalance = ledgerService.deposit(accountId, amount);
        return ResponseEntity.ok(newBalance); // Respond with 200 OK and the new balance
    }

    @PutMapping("/withdraw/{accountId}")
    public ResponseEntity<Double> withdraw(@PathVariable String accountId, @RequestParam double amount) {
        if (amount <= 0) {
            return ResponseEntity.badRequest().body(null); // Same validation for withdrawal
        }
        try {
            double newBalance = ledgerService.withdraw(accountId, amount);
            return ResponseEntity.ok(newBalance);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null); // Handle possible exceptions, e.g., insufficient funds
        }
    }

    @GetMapping("/balance/{accountId}")
    public ResponseEntity<Double> getBalance(@PathVariable String accountId) {
        double balance = ledgerService.getCurrentBalance(accountId);
        return ResponseEntity.ok(balance);
    }
    
    @GetMapping("/")
    public String home() {
        return "Welcome to the application!";
    }
}
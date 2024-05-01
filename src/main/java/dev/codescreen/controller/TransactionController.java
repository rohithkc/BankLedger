package dev.codescreen.controller;

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
    public double deposit(@PathVariable String accountId, @RequestParam double amount) {
        return ledgerService.deposit(accountId, amount);
    }

    @PutMapping("/withdraw/{accountId}")
    public double withdraw(@PathVariable String accountId, @RequestParam double amount) {
        return ledgerService.withdraw(accountId, amount);
    }

    @GetMapping("/balance/{accountId}")
    public double getBalance(@PathVariable String accountId) {
        return ledgerService.getCurrentBalance(accountId);
    }
}
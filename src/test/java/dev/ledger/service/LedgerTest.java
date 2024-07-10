package dev.ledger.service;

import dev.ledger.repository.TransactionRepository;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class LedgerServiceTest {
    private Ledger service;
    private TransactionRepository repository;

    @BeforeEach
    void setUp() {
        repository = new TransactionRepository();
        service = new Ledger(repository);
    }

    /*
     * Test regular deposit
     */

    @Test
    void testDeposit() {
        service.deposit("123", 100.0);
        assertEquals(100.0, service.getCurrentBalance("123"), "Balance should be 100 after deposit");
    }

    /*
     * Test deposit $0
     */
    
     @Test
     void testDepositNone() {
         service.deposit("123", 0.0);
         assertEquals(0.0, service.getCurrentBalance("123"), "Balance should be 0 after deposit");
     }

    /*
     * Test regular withdraw
     */

    @Test
    void testWithdraw() {
        service.deposit("123", 200.0);
        service.withdraw("123", 100.0);
        assertEquals(100.0, service.getCurrentBalance("123"), "Balance should be 100 after withdrawal");
    }

    /*
     * Test whether a user is allowed to withdraw more than current balance
     */

     void testWithdrawMoreThanBalance(){
        // Setup initial conditions
        String accountId = "12345";

        // Assuming a method to add initial balance
        service.deposit(accountId, 100);

        // Execute and verify
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            service.withdraw(accountId, 150);
        });

        // Optionally check the error message
        String expectedMessage = "Insufficient funds";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage), "Error message should indicate insufficient funds");
     }
}
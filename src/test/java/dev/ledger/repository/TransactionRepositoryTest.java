package dev.ledger.repository;
import dev.ledger.models.EventType;
import java.util.Date;
import dev.ledger.models.Transaction;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

public class TransactionRepositoryTest {
    private TransactionRepository repository;

    @BeforeEach
    void setUp() {
        repository = new TransactionRepository();
    }

    /* 
     * Test to check if my transaction that's created is being saved and that there's only one in the transaction history and it matches
     */

    @Test
    void testSaveTransaction() {
        Transaction transaction = new Transaction("tx100", "acc123", 100.0, new Date(), EventType.LOAD);
        repository.save(transaction);

        List<Transaction> results = repository.findByAccountId("acc123");
        assertEquals(1, results.size(), "Should contain exactly one transaction.");
        assertEquals(transaction, results.get(0), "The retrieved transaction should be the same as the saved one.");
    }


    /*
     * Test to check if both transactions are being saved
     */

    @Test
    void testFindByAccountIdWithMultipleTransactions() {
        Transaction transaction1 = new Transaction("tx101", "acc123", 200.0, new Date(), EventType.LOAD);
        Transaction transaction2 = new Transaction("tx102", "acc123", -50.0, new Date(), EventType.AUTHORIZATION);
        repository.save(transaction1);
        repository.save(transaction2);

        List<Transaction> results = repository.findByAccountId("acc123");
        assertEquals(2, results.size(), "Should contain exactly two transactions.");
    }

    /*
     * Test to check account history with no transactions
     */
    @Test
    void testFindByAccountIdWithNoTransactions() {
        List<Transaction> results = repository.findByAccountId("accXYZ");
        assertTrue(results.isEmpty(), "Should return an empty list for non-existent account.");
    }

    /*
     * Test a very large transaction
     */

     @Test
     void testVeryLargeTransactionAmount() {
         Transaction largeAmountTransaction = new Transaction("tx201", "acc124", Double.MAX_VALUE, new Date(), EventType.LOAD);
         repository.save(largeAmountTransaction);
         
         List<Transaction> results = repository.findByAccountId("acc124");
         assertEquals(Double.MAX_VALUE, results.get(0).getAmount(), "Transaction amount should handle very large values.");
     }

    /*
     * Test a very small transaction
    */
    @Test
    void testVerySmallTransactionAmount() {
        Transaction smallAmountTransaction = new Transaction("tx202", "acc125", -Double.MAX_VALUE, new Date(), EventType.AUTHORIZATION);
        repository.save(smallAmountTransaction);
        
        List<Transaction> results = repository.findByAccountId("acc125");
        assertEquals(-Double.MAX_VALUE, results.get(0).getAmount(), "Transaction amount should handle very small (negative) values.");
    }

    /*
     * Test Invalid ID
     */

     @Test
     void testInvalidAccountId() {
        assertThrows(NullPointerException.class, () -> {
            repository.save(new Transaction("tx203", null, 100.0, new Date(), EventType.LOAD));
        }, "Should throw NullPointerException for null account ID.");
    }

     /*
     * Test to see if NUll transaction gets saved (It shouldn't)
     */
    @Test
    void testSaveNullTransaction() {
        assertThrows(IllegalArgumentException.class, () -> {
            repository.save(null);
        }, "Saving a null transaction should throw IllegalArgumentException.");
    }

    /*
     * Test to find transactions with null account id -> should return an IllegalArgumentException
     */
    @Test
    void testFindTransactionsForNullAccountId() {
        assertThrows(IllegalArgumentException.class, () -> {
            repository.findByAccountId(null);
        }, "Querying transactions with a null account ID should throw IllegalArgumentException.");
    }
}
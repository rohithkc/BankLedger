package dev.ledger.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;

import dev.ledger.controller.TransactionController;
import dev.ledger.service.Ledger;

public class TransactionControllerTest {

    private MockMvc mockMvc;

    @Mock
    private Ledger ledgerService;

    @InjectMocks
    private TransactionController transactionController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        mockMvc = standaloneSetup(transactionController).build();
    }

    @Test
    public void testDeposit() throws Exception {
        // Mock the behavior of the ledgerService to return a specific balance after a
        // deposit
        when(ledgerService.deposit("1", 100.0)).thenReturn(150.0);

        // Perform a PUT request to the deposit endpoint and pass parameters
        mockMvc.perform(put("/api/transactions/deposit/1") // Simulate sending a deposit amount via query parameter
                .param("amount", "100.0"))
                .andExpect(status().isOk()) // Expect a 200 OK status code
                .andExpect(content().string("150.0")); // Expect the response content to be "150.0", which is the new
                                                       // balance

        verify(ledgerService).deposit("1", 100.0);
    }

    @Test
    public void testWithdraw() throws Exception {
        // Mock the behavior of the ledgerService to return a specific balance after a
        // deposit
        when(ledgerService.withdraw("1", 50.0)).thenReturn(100.0);

        mockMvc.perform(put("/api/transactions/withdraw/1") // Simulate sending a withdraw amount via query parameter
                .param("amount", "50.0"))
                .andExpect(status().isOk()) // Expect a 200 OK status code
                .andExpect(content().string("100.0")); // Expect the response content to be "100.0", which is the new
                                                       // balance

        verify(ledgerService).withdraw("1", 50.0);
    }

    @Test
    public void testGetBalance() throws Exception {
        // Mock the behavior of the ledgerService to return a specific balance when
        // requested
        when(ledgerService.getCurrentBalance("1")).thenReturn(200.0);

        // Perform a GET request to the balance endpoint
        mockMvc.perform(get("/api/transactions/balance/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("200.0"));

        // Verify that the getCurrentBalance method in the ledgerService was called
        // exactly once with the specified accountId
        verify(ledgerService).getCurrentBalance("1");
    }

    @Test
    public void testDepositNegativeAmount() throws Exception {
        // Perform a PUT request to the deposit endpoint with a negative amount
        mockMvc.perform(put("/api/transactions/deposit/1")
                .param("amount", "-100.0"))
                .andExpect(status().isBadRequest()); // Expect a 400 Bad Request status code

        // Verify that the deposit method in the ledgerService was never called since
        // the amount is negative
        verify(ledgerService, never()).deposit(anyString(), anyDouble());
    }
}
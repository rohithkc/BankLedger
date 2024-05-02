package dev.codescreen.controller;

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
import dev.codescreen.service.Ledger;

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
        when(ledgerService.deposit("1", 100.0)).thenReturn(150.0);

        mockMvc.perform(put("/api/transactions/deposit/1")
                .param("amount", "100.0"))
                .andExpect(status().isOk())
                .andExpect(content().string("150.0"));

        verify(ledgerService).deposit("1", 100.0);
    }

    @Test
    public void testWithdraw() throws Exception {
        when(ledgerService.withdraw("1", 50.0)).thenReturn(100.0);

        mockMvc.perform(put("/api/transactions/withdraw/1")
                .param("amount", "50.0"))
                .andExpect(status().isOk())
                .andExpect(content().string("100.0"));

        verify(ledgerService).withdraw("1", 50.0);
    }

    @Test
    public void testGetBalance() throws Exception {
        when(ledgerService.getCurrentBalance("1")).thenReturn(200.0);

        mockMvc.perform(get("/api/transactions/balance/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("200.0"));

        verify(ledgerService).getCurrentBalance("1");
    }

    @Test
    public void testDepositNegativeAmount() throws Exception {
        mockMvc.perform(put("/api/transactions/deposit/1")
                .param("amount", "-100.0"))
                .andExpect(status().isBadRequest());  // Assuming your controller handles this case

        verify(ledgerService, never()).deposit(anyString(), anyDouble());
    }
}
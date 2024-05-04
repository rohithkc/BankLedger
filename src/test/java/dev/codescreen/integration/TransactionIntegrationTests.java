package dev.codescreen;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TransactionIntegrationTests {

    @Autowired
    private TestRestTemplate restTemplate;

    @LocalServerPort
    private int port;

    private String createUrl(String endpoint) {
        return "http://localhost:" + port + "/api/transactions" + endpoint;
    }

    @Test
    public void testDepositFunctionality() {
        String url = createUrl("/deposit/1?amount=100.0");
        HttpEntity<Double> request = new HttpEntity<>(null, new HttpHeaders());

        // Using exchange to perform a PUT request
        ResponseEntity<Double> response = restTemplate.exchange(url, HttpMethod.PUT, request, Double.class);

        assertEquals(HttpStatus.OK, response.getStatusCode(), "Expected OK response for deposit");
        assertNotNull(response.getBody(), "Expected a non-null response for deposit");
        assertTrue(response.getBody() >= 100.0, "Deposit amount not reflected correctly");

        // Verifying the balance
        ResponseEntity<Double> balanceResponse = restTemplate.getForEntity(createUrl("/balance/1"), Double.class);
        assertEquals(HttpStatus.OK, balanceResponse.getStatusCode(), "Failed to get balance");
        assertEquals(response.getBody(), balanceResponse.getBody(), "Balance does not match after deposit");
    }

    @Test
    public void testWithdrawFunctionality() {
        // Preparing for a withdrawal by ensuring there is enough balance
        restTemplate.exchange(createUrl("/deposit/2?amount=200.0"), HttpMethod.PUT, new HttpEntity<>(null, new HttpHeaders()), Double.class);

        String withdrawUrl = createUrl("/withdraw/2?amount=50.0");
        ResponseEntity<Double> withdrawResponse = restTemplate.exchange(withdrawUrl, HttpMethod.PUT, new HttpEntity<>(null, new HttpHeaders()), Double.class);

        assertEquals(HttpStatus.OK, withdrawResponse.getStatusCode(), "Expected OK response for withdrawal");
        assertNotNull(withdrawResponse.getBody(), "Expected a non-null response for withdrawal");
        assertTrue(withdrawResponse.getBody() <= 150.0, "Withdrawal amount not reflected correctly");

        // Checking the balance after withdrawal
        ResponseEntity<Double> balanceResponse = restTemplate.getForEntity(createUrl("/balance/2"), Double.class);
        assertEquals(HttpStatus.OK, balanceResponse.getStatusCode(), "Failed to get balance after withdrawal");
        assertEquals(withdrawResponse.getBody(), balanceResponse.getBody(), "Balance does not match after withdrawal");
    }
}
package com.fullStack.expenseTracker.gateway;

import com.fullStack.expenseTracker.controllers.TransactionController;
import com.fullStack.expenseTracker.controllers.SignUpController;
import com.fullStack.expenseTracker.services.ReportService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;

public class GatewayControllerTest {

    @InjectMocks
    private GatewayController gatewayController;

    @Mock
    private TransactionController transactionController;

    @Mock
    private SignUpController signUpController;

    @Mock
    private ReportService reportService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testRequestHandler() {
        // Setup mock request and expected responses
        String requestType = "ADD_TRANSACTION"; // Example request type
        String response = "Transaction added successfully"; // Expected response

        when(transactionController.addTransaction(any())).thenReturn(response);

        // Actual invocation
        String result = gatewayController.requestHandler(requestType, null); // Passes parameters as needed

        // Assertions
        assertEquals(response, result);
        verify(transactionController, times(1)).addTransaction(any()); // Verify interaction with the transaction controller
    }

    @Test
    public void testGetStatus() {
        // Setup mock service status responses
        String serviceStatus = "All services operational"; // Example status response
        when(reportService.checkServiceStatus()).thenReturn(serviceStatus);

        // Actual invocation
        String result = gatewayController.getStatus();

        // Assertions
        assertEquals(serviceStatus, result);
        verify(reportService, times(1)).checkServiceStatus(); // Verify interaction with the report service
    }
}

package com.fullStack.expenseTracker.gateway;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class GatewayControllerTest {

    @InjectMocks
    private GatewayController gatewayController;

    @Mock
    private GatewayService gatewayService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testHandleIncomingRequest() {
        // Arrange: prepare your request and expected response
        String request = "sample request";
        String expectedResponse = "sample response";

        // Mock the GatewayService response
        when(gatewayService.routeRequests(any(String.class))).thenReturn(expectedResponse);

        // Act: call the handleIncomingRequest
        String actualResponse = gatewayController.handleIncomingRequest(request);

        // Assert: validate the response is as expected
        assertEquals(expectedResponse, actualResponse);

        // Verify that the routeRequests method was called with the correct parameter
        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        verify(gatewayService).routeRequests(captor.capture());
        assertEquals(request, captor.getValue());
    }
}

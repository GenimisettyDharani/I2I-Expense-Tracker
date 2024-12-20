package com.fullStack.expenseTracker.gateway;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;

import static org.junit.jupiter.api.Assertions.*;

class GatewayServiceTest {

    private GatewayService gatewayService;

    @BeforeEach
    void setUp() {
        // Initialize the GatewayService before each test
        gatewayService = new GatewayService();
        gatewayService.initializeGateway(); // Ensure gateway is initialized
    }

    @Test
    void testInitializeGateway() {
        // Validate that the gateway is correctly initialized
        assertNotNull(gatewayService.getConfigurations(), "Configurations should not be null after initialization");
        assertTrue(gatewayService.isGatewayReady(), "Gateway should be ready after initialization");
        
        // Any additional checks according to GatewayService implementation
    }

    @Test
    void testRouteRequests() {
        // Arrange: Prepare a mock request that the gateway would receive
        Request mockRequest = mock(Request.class);
        when(mockRequest.getParameter("serviceType")).thenReturn("payment");

        // Act: Route the requests using GatewayService
        Response response = gatewayService.routeRequests(mockRequest);

        // Assert: Verify that the response is as expected and services are called correctly
        assertNotNull(response, "Response should not be null");
        assertEquals("success", response.getStatus(), "Response status should be 'success' for valid requests");
        
        // Verify that the appropriate service was called based on the request
        verify(mockRequest).getParameter("serviceType");
        
        // Additional assertions and verifications as per the implementation
    }
}

package com.fullStack.expenseTracker.gateway;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/gateway")
public class GatewayController {

    @Autowired
    private GatewayService gatewayService;

    /**
     * This function acts as the entry point for the gateway service, accepting requests and 
     * initiating the routing process through `GatewayService`.
     * It ensures the request is properly formatted and routed.
     *
     * @param requestPayload The incoming request data
     * @return ResponseEntity containing the response from the routed backend service
     */
    @PostMapping("/route")
    public ResponseEntity<?> handleIncomingRequest(@RequestBody String requestPayload) {
        // Validate incoming request payload (you can extend validation as necessary)
        if (requestPayload == null || requestPayload.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                 .body("Request payload cannot be null or empty");
        }

        try {
            // Route the request using the GatewayService
            Object response = gatewayService.routeRequests(requestPayload);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            // Handle exceptions and return a response
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body("Failed to process the request: " + e.getMessage());
        }
    }
}

package com.fullStack.expenseTracker.gateway;

import com.fullStack.expenseTracker.controllers.TransactionController;
import com.fullStack.expenseTracker.controllers.SignUpController;
import com.fullStack.expenseTracker.services.ReportService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/gateway")
public class GatewayController {

    private final TransactionController transactionController;
    private final SignUpController signUpController;
    private final ReportService reportService;

    public GatewayController(TransactionController transactionController, SignUpController signUpController, ReportService reportService) {
        this.transactionController = transactionController;
        this.signUpController = signUpController;
        this.reportService = reportService;
    }

    @PostMapping("/request")
    public ResponseEntity<?> requestHandler(@RequestBody RequestDTO request) {
        try {
            // Example routing based on request type, should be replaced with actual logic
            switch (request.getType()) {
                case "ADD_TRANSACTION":
                    return transactionController.addTransaction(request.getPayload());
                case "SIGNUP":
                    return signUpController.signupUser(request.getPayload());
                default:
                    return ResponseEntity.badRequest().body("Invalid request type.");
            }
        } catch (Exception e) {
            // Log error and return meaningful error response
            return handleErrors(e);
        }
    }
    
    @GetMapping("/status")
    public ResponseEntity<?> getStatus() {
        try {
            boolean isServiceUp = reportService.checkServiceStatus();
            return ResponseEntity.ok(isServiceUp ? "All services operational" : "Some services are down");
        } catch (Exception e) {
            return handleErrors(e);
        }
    }

    private ResponseEntity<?> handleErrors(Exception e) {
        // Log the error message for debugging
        // Ideally, use a logging framework like SLF4J with Logback or Log4j
        System.err.println("Error occurred: " + e.getMessage());
        return ResponseEntity.status(500).body("Internal server error: " + e.getMessage());
    }
}

class RequestDTO {
    private String type;
    private Object payload;

    // Getters and setters

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Object getPayload() {
        return payload;
    }

    public void setPayload(Object payload) {
        this.payload = payload;
    }
}

package com.fullStack.expenseTracker.gateway;

import com.fullStack.expenseTracker.services.TransactionService;
import com.fullStack.expenseTracker.services.AuthService;
import com.fullStack.expenseTracker.services.ReportService;
import com.fullStack.expenseTracker.services.SavedTransactionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GatewayService {
    private static final Logger logger = LoggerFactory.getLogger(GatewayService.class);

    // Function to set up the communication routes for the gateway service
    public void setupRoutes() {
        try {
            // Example of how routes might be set up
            // This is a placeholder for actual route setup logic
            // e.g., route("/fetchTransactions", TransactionService::fetchAllTransactions);
            // e.g., route("/verifyUser", AuthService::verifyUserCredentials);
            logger.info("Routes have been set up successfully.");
        } catch (Exception e) {
            logger.error("Error setting up routes: ", e);
            // Custom error handling logic can go here
        }
    }

    // Function to initialize the gateway service
    public void initialize() {
        try {
            // Check service availability before proceeding
            ReportService.getReportData();

            // Additional initialization logic can go here

            logger.info("Gateway service initialized successfully.");
        } catch (Exception e) {
            logger.error("Error during gateway service initialization: ", e);
            // Handle initialization errors appropriately
        }
    }

    // Function responsible for managing incoming requests
    public void handleRequests(Request request) {
        try {
            // Route the requests to the appropriate services
            if (request.getType().equals("TRANSACTION")) {
                SavedTransactionService.findAllSavedTransactions(request);
            } else if (request.getType().equals("AUTH")) {
                AuthService.authenticateUser(request);
            }
            // Additional routing logic can go here

            logger.info("Handled request of type: " + request.getType());
        } catch (Exception e) {
            logger.error("Error handling request: ", e);
            // Call handleErrors to properly forward error messages to the client
            handleErrors(e);
        }
    }

    // Function to handle errors during service calls
    public void handleErrors(Exception e) {
        try {
            String errorMessage = TransactionService.handleTransactionErrors(e);
            // Forward the error message to the client
            // Respond to the client with meaningful feedback
            logger.error("Handled error: " + errorMessage);
        } catch (Exception ex) {
            logger.error("Error handling the initial error: ", ex);
            // Handle any secondary errors that arise during error handling
        }
    }
}

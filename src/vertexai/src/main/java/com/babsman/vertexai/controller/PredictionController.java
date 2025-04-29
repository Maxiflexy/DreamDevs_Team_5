package com.babsman.vertexai.controller;

import com.babsman.vertexai.model.PredictionRequest;
import com.babsman.vertexai.model.SimplifiedPredictionRequest;
import com.babsman.vertexai.model.VertexAiPredictionResponse;
import com.babsman.vertexai.service.VertexAiService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/predict")
public class PredictionController {

    private final VertexAiService vertexAiService;

    public PredictionController(VertexAiService vertexAiService) {
        this.vertexAiService = vertexAiService;
    }

    /**
     * Simple test endpoint that returns basic service info
     */
    @GetMapping
    public String getServiceInfo() {
        return vertexAiService.getPrediction();
    }

    /**
     * Main prediction endpoint that handles the actual ML predictions
     */
    @PostMapping("/model")
    public ResponseEntity<?> getPrediction(@RequestBody PredictionRequest request) {

        List<List<Object>> instances = request.getInstances();

        if (instances == null || instances.isEmpty()) {
            return ResponseEntity.badRequest().body("Request must contain either 'instances' or both 'amount' and 'current_balance' fields.");
        }

        try {
            VertexAiPredictionResponse predictionResponse = vertexAiService.getPrediction(request.getInstances());
            return ResponseEntity.ok(predictionResponse);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error calling Vertex AI prediction endpoint: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Invalid input data format: " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An unexpected error occurred: " + e.getMessage());
        }
    }

//    /**
//     * New simplified endpoint that accepts the simplified format and converts it to the required format
//     */
//    @PostMapping("/simple")
//    public ResponseEntity<?> getSimplifiedPrediction(@RequestBody SimplifiedPredictionRequest request) {
//        try {
//            // Convert the simplified request to the format expected by the service
//            List<List<Object>> instances = new ArrayList<>();
//
//            // Create the instance with the format [1, amount, current_balance, difference]
//            double amount = request.getAmount();
//            double currentBalance = request.getCurrentBalance();
//            double difference = currentBalance - amount;
//
//            // Always use 1 as the first element, then amount, current balance, and the calculated difference
//            List<Object> instance = Arrays.asList(
//                    1,          // Constant value 1
//                    amount,     // The requested amount
//                    currentBalance, // The current balance
//                    difference  // The difference (current_balance - amount)
//            );
//
//            instances.add(instance);
//
//            // Call the service with the transformed data
//            VertexAiPredictionResponse predictionResponse = vertexAiService.getPrediction(instances);
//            return ResponseEntity.ok(predictionResponse);
//        } catch (IOException e) {
//            e.printStackTrace();
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                    .body("Error calling Vertex AI prediction endpoint: " + e.getMessage());
//        } catch (IllegalArgumentException e) {
//            e.printStackTrace();
//            return ResponseEntity.badRequest().body("Invalid input data format: " + e.getMessage());
//        } catch (Exception e) {
//            e.printStackTrace();
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                    .body("An unexpected error occurred: " + e.getMessage());
//        }
//    }
}

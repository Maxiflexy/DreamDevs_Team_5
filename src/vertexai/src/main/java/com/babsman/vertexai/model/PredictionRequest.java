package com.babsman.vertexai.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


// This class represents the structure of the request body expected from the frontend.
// It assumes the frontend sends the data in a similar format to the original JSON,
// but the backend will transform it for the Vertex AI API.
// You might adjust this based on what your frontend actually sends.
@JsonIgnoreProperties(ignoreUnknown = true)
public class PredictionRequest {


    private static final double DIVISION_FACTOR = 1500.0;

    // Using List<List<Object>> to be flexible with the incoming JSON structure
    // from the frontend. The backend service will then extract and order the values.
    // A more robust approach would be to define a specific class for the instance features
    // if the frontend structure is fixed.
    private List<List<Object>> instances;

    // New fields for simplified format
    private Double amount;

    @JsonProperty("current_balance")  // Match the exact field name in JSON
    private Double currentBalance;

    public List<List<Object>> getInstances() {
        // If instances is already set (original format was used), return it
        if (instances != null && !instances.isEmpty()) {
            return instances;
        }

        // If simplified format was used, convert it to the required format
        if (amount != null && currentBalance != null) {

            double processedAmount = amount / DIVISION_FACTOR;
            double processedCurrentBalance = currentBalance / DIVISION_FACTOR;

            List<List<Object>> formattedInstances = new ArrayList<>();
            double difference = processedCurrentBalance - processedAmount;

            // Create single instance with format [1, amount, current_balance, difference]
            List<Object> instance = Arrays.asList(
                    1,                // Constant value 1
                    processedAmount,           // The requested amount
                    processedCurrentBalance,   // The current balance
                    difference        // The difference (current_balance - amount)
            );

            formattedInstances.add(instance);
            return formattedInstances;
        }

        // Return empty list if neither format was provided correctly
        return new ArrayList<>();
    }

    public void setInstances(List<List<Object>> instances) {
        this.instances = instances;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Double getCurrentBalance() {
        return currentBalance;
    }

    public void setCurrentBalance(Double currentBalance) {
        this.currentBalance = currentBalance;
    }

    @Override
    public String toString() {
        return "PredictionRequest{" +
                "instances=" + getInstances() +
                ", amount=" + amount +
                ", currentBalance=" + currentBalance +
                '}';
    }
}

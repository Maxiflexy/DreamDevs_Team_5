package com.babsman.vertexai.model;

/**
 * This class represents the simplified request format from the client
 * with only amount and current_balance fields.
 */
public class SimplifiedPredictionRequest {

    private double amount;
    private double current_balance;

    // Getters and setters
    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public double getCurrentBalance() {
        return current_balance;
    }

    public void setCurrentBalance(double current_balance) {
        this.current_balance = current_balance;
    }

    @Override
    public String toString() {
        return "SimplifiedPredictionRequest{" +
                "amount=" + amount +
                ", current_balance=" + current_balance +
                '}';
    }
}
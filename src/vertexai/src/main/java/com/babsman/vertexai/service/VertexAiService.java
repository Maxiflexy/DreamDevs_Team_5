package com.babsman.vertexai.service;

import com.babsman.vertexai.config.GoogleCloudConfig;
import com.babsman.vertexai.model.VertexAiPredictionResponse;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.aiplatform.v1.EndpointName;
import com.google.cloud.aiplatform.v1.PredictResponse;
import com.google.cloud.aiplatform.v1.PredictionServiceClient;
import com.google.cloud.aiplatform.v1.PredictionServiceSettings;
import com.google.protobuf.ListValue;
import com.google.protobuf.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class VertexAiService {
    private final GoogleCloudConfig googleCloudConfig;
    private final PredictionServiceClient predictionServiceClient;

    @Autowired
    public VertexAiService(GoogleCloudConfig googleCloudConfig,
                          GoogleCredentials credentials) throws IOException {
        this.googleCloudConfig = googleCloudConfig;
        
        // Initialize the Vertex AI client with credentials
        PredictionServiceSettings predictionServiceSettings = PredictionServiceSettings.newBuilder()
            .setEndpoint(googleCloudConfig.getLocation() + "-aiplatform.googleapis.com:443")
            .setCredentialsProvider(() -> credentials)
            .build();
        
        this.predictionServiceClient = PredictionServiceClient.create(predictionServiceSettings);
    }

    public String getPrediction() {
        return "Prediction service initialized with project: " + googleCloudConfig.getProjectId();
    }

    public VertexAiPredictionResponse getPrediction(List<List<Object>> instances) throws IOException {
        // Create the endpoint path
        EndpointName endpointName = EndpointName.of(
            googleCloudConfig.getProjectId(),
            googleCloudConfig.getLocation(),
            googleCloudConfig.getEndpointId()
        );

        // Convert instances to Protobuf format
        List<Value> instanceValues = new ArrayList<>();
        for (List<Object> instance : instances) {
            ListValue.Builder listValueBuilder = ListValue.newBuilder();
            for (Object value : instance) {
                if (value instanceof Number) {
                    listValueBuilder.addValues(Value.newBuilder()
                        .setNumberValue(((Number) value).doubleValue())
                        .build());
                } else if (value instanceof Boolean) {
                    listValueBuilder.addValues(Value.newBuilder()
                        .setBoolValue((Boolean) value)
                        .build());
                } else if (value instanceof String) {
                    listValueBuilder.addValues(Value.newBuilder()
                        .setStringValue((String) value)
                        .build());
                } else {
                    listValueBuilder.addValues(Value.newBuilder()
                        .setNumberValue(0.0)
                        .build());
                }
            }
            instanceValues.add(Value.newBuilder().setListValue(listValueBuilder.build()).build());
        }

        // Create the prediction request
        com.google.cloud.aiplatform.v1.PredictRequest predictRequest = 
            com.google.cloud.aiplatform.v1.PredictRequest.newBuilder()
                .setEndpoint(endpointName.toString())
                .addAllInstances(instanceValues)
                .build();

        // Make the prediction call
        PredictResponse predictResponse = predictionServiceClient.predict(predictRequest);

        // Convert the response to our format
        VertexAiPredictionResponse response = new VertexAiPredictionResponse();
        
        // Extract predictions
        List<Integer> predictions = new ArrayList<>();
        for (Value prediction : predictResponse.getPredictionsList()) {
            // Assuming the model returns a number that we can interpret as an integer
            double predValue = prediction.getNumberValue();
            predictions.add((int) Math.round(predValue));
        }
        
        response.setPredictions(predictions);
        response.setDeployedModelId(predictResponse.getDeployedModelId());
        response.setModel(predictResponse.getModel());
        response.setModelDisplayName(predictResponse.getModelDisplayName());
        response.setModelVersionId("1"); // Set this if available in the response

        return response;
    }

    // Clean up resources
    public void close() {
        if (predictionServiceClient != null) {
            predictionServiceClient.close();
        }
    }
}

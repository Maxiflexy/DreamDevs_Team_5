package com.babsman.vertexai.model;

import java.util.List;

// This class represents the structure of the response received from the Vertex AI endpoint.
// The exact structure depends on your model's output.
// This is a common structure, but you might need to adjust it.
public class VertexAiPredictionResponse {

    // The 'predictions' field typically contains a list of prediction results,
    // one for each instance sent in the request.
    // The type of the elements in the list (e.g., List<Double>, List<Map<String, Object>>)
    // depends on your model's output format.
    // Using List<Integer> for the given format.
    private List<Integer> predictions;

    // The 'deployedModelId' field identifies the model that served the prediction.
    private String deployedModelId;

    // The 'model' field might contain the model resource name.
    private String model;

    // The 'modelDisplayName' field might contain the display name of the model.
    private String modelDisplayName;

    // The 'modelVersionId' field might contain the version of the model.
    private String modelVersionId;

    // Getters and Setters
    public List<Integer> getPredictions() {
        return predictions;
    }

    public void setPredictions(List<Integer> predictions) {
        this.predictions = predictions;
    }

    public String getDeployedModelId() {
        return deployedModelId;
    }

    public void setDeployedModelId(String deployedModelId) {
        this.deployedModelId = deployedModelId;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getModelDisplayName() {
        return modelDisplayName;
    }

    public void setModelDisplayName(String modelDisplayName) {
        this.modelDisplayName = modelDisplayName;
    }

    public String getModelVersionId() {
        return modelVersionId;
    }

    public void setModelVersionId(String modelVersionId) {
        this.modelVersionId = modelVersionId;
    }

    @Override
    public String toString() {
        return "VertexAiPredictionResponse{" +
               "predictions=" + predictions +
               ", deployedModelId='" + deployedModelId + '\'' +
               ", model='" + model + '\'' +
               ", modelDisplayName='" + modelDisplayName + '\'' +
               ", modelVersionId='" + modelVersionId + '\'' +
               '}';
    }
}

# VertexAI predict endpoint

## How to Use This Image

1. Pull the image:
   ```
   docker pull babsman/my-vertexai-app:latest
   ```

2. Run the container (mount your service account file):
   ```
   docker run -p 8080:8080 \
     -e GOOGLE_APPLICATION_CREDENTIALS=/app/service-account.json \
     -v /path/to/service-account.json:/app/service-account.json \
     babsman/my-vertexai-app:latest
   ```
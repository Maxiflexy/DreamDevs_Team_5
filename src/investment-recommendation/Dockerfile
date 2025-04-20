# Dockerfile
FROM node:22

# Create app directory
WORKDIR /app

# Copy package files and install dependencies
COPY package*.json ./
RUN npm install

# Copy rest of the app
COPY . .

# Expose port
EXPOSE 8080

# Start app
CMD ["node", "src/app.js"]

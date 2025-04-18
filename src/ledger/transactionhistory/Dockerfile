# Build stage
FROM maven:3.9-eclipse-temurin-17-alpine AS builder

# Set working directory
WORKDIR /app

# Copy the POM file
COPY ./pom.xml ./pom.xml

# Download all dependencies
# This layer is cached if pom.xml doesn't change
RUN mvn dependency:go-offline -B

# Copy source code
COPY ./src ./src

# Build the application
RUN mvn package -DskipTests

# Runtime stage
FROM eclipse-temurin:17.0.4.1_1-jre-alpine@sha256:e1506ba20f0cb2af6f23e24c7f8855b417f0b085708acd9b85344a884ba77767

# Set working directory
WORKDIR /app

# Copy the built jar file from the builder stage
COPY --from=builder /app/target/*.jar ./app.jar

# Expose the port
ENV PORT=8080
EXPOSE 8080

# Set JVM options
ENV JVM_OPTS="-XX:+UnlockExperimentalVMOptions -XX:+UseCGroupMemoryLimitForHeap -Xms256m -Xmx512m"

# Run the application
CMD ["sh", "-c", "java $JVM_OPTS -jar app.jar"]
# Multi-stage Dockerfile for Spring Boot and Gradle

# Stage 1: Build the application using Gradle
FROM gradle:7.6-jdk17 AS builder
WORKDIR /home/gradle/project
COPY --chown=gradle:gradle . .
RUN gradle build --no-daemon -x test

# Stage 2: Create a smaller image with only the JAR
FROM openjdk:17-jdk-slim
WORKDIR /app
# Copy the fat jar from the builder stage
COPY --from=builder /home/gradle/project/build/libs/*.jar app.jar

# Expose the application port
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]

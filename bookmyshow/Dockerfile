# Stage 1: Build using Gradle
FROM gradle:7.6-jdk17 AS builder

WORKDIR /home/gradle/project

# Copy build files first (leverage Docker cache)
COPY build.gradle settings.gradle ./
COPY gradle gradle
RUN gradle clean assemble --no-daemon || true

# Copy rest of source
COPY . .

# 👇 This forces annotation processing
RUN gradle build --no-daemon -x test --info

# Stage 2: Runtime image
FROM openjdk:17-jdk-slim
WORKDIR /app
COPY --from=builder /home/gradle/project/build/libs/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]

# Use OpenJDK 21 for building
FROM openjdk:21-jdk-slim AS build

# Set working directory
WORKDIR /app

# Copy all project files
COPY . .

# Make gradlew executable
RUN chmod +x ./gradlew

# Build the application
RUN ./gradlew clean bootJar --no-daemon --stacktrace

# Production stage
FROM openjdk:21-jre-slim

# Set working directory
WORKDIR /app

# Copy the JAR file from build stage
COPY --from=build /app/build/libs/*.jar app.jar

# Expose port
EXPOSE 8080

# Run the application
CMD ["java", "-jar", "app.jar", "--spring.profiles.active=prod"]
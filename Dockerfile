# ---- Build Stage: use JDK 21 to run Gradle ----
FROM eclipse-temurin:21-jdk AS builder

WORKDIR /workspace

# Copy Gradle wrapper first (better build cache)
COPY gradlew gradle.* ./
COPY gradle ./gradle

RUN chmod +x ./gradlew

# Copy the rest of the project
COPY . .

# Build the Spring Boot jar
RUN ./gradlew clean bootJar --no-daemon

# ---- Runtime Stage: slim JRE 21 image ----
FROM eclipse-temurin:21-jre

WORKDIR /app

# Copy the built jar from the builder image
COPY --from=builder /workspace/build/libs/*SNAPSHOT.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]

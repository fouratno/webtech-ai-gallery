# ---- Build Stage ----
# Use explicit jammy tags to avoid missing images on Render's Docker registry mirror.
FROM eclipse-temurin:21-jdk-jammy AS builder

WORKDIR /workspace
COPY . .
RUN chmod +x ./gradlew
RUN ./gradlew clean bootJar --no-daemon

# ---- Runtime Stage ----
FROM eclipse-temurin:21-jre-jammy AS runner

WORKDIR /app
COPY --from=builder /workspace/build/libs/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]

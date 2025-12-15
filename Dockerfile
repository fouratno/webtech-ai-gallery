# ---- Build Stage ----
FROM eclipse-temurin:21-jdk AS builder

WORKDIR /workspace
COPY . .
RUN chmod +x ./gradlew
RUN ./gradlew clean bootJar --no-daemon

# ---- Runtime Stage ----
FROM eclipse-temurin:21-jre AS runner

WORKDIR /app
COPY --from=builder /workspace/build/libs/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]

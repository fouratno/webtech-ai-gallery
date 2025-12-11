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
ENV PORT=8080
EXPOSE 8080
ENTRYPOINT ["sh", "-c", "java -jar app.jar --server.port=${PORT} --server.address=0.0.0.0"]

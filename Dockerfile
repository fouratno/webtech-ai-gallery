# ---- Build-Stage: JDK 25 für Gradle-Toolchain (Java 25) ----
FROM eclipse-temurin:25-jdk AS builder

WORKDIR /workspace

# Projekt in das Image kopieren
COPY . .

# Gradle Wrapper ausführbar machen
RUN chmod +x ./gradlew

# Spring Boot Jar bauen
RUN ./gradlew clean bootJar --no-daemon

# ---- Runtime-Stage: schlankes JRE 25 Image ----
FROM eclipse-temurin:25-jre

WORKDIR /app

# Fertiges Jar aus der Build-Stage kopieren
COPY --from=builder /workspace/build/libs/*.jar app.jar

# Render nutzt PORT, Spring Boot hört intern auf 8080
ENV PORT=8080
EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]

# =========================
# Build Stage
# =========================
FROM maven:3.9.6-eclipse-temurin-21 AS build

WORKDIR /app

# Copy pom.xml first (cache dependencies)
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copy source code
COPY src ./src

# Build application
RUN mvn clean package -DskipTests -B


# =========================
# Runtime Stage
# =========================
FROM eclipse-temurin:21-jre

WORKDIR /app

# Create non-root user for security
RUN groupadd -r spring && useradd -r -g spring spring
USER spring:spring

# Copy JAR from build stage with correct permissions
COPY --from=build --chown=spring:spring /app/target/*.jar app.jar

# Expose port
EXPOSE 8080

# Healthcheck using curl (pre-installed in temurin)
HEALTHCHECK --interval=30s --timeout=3s --start-period=60s --retries=3 \
    CMD curl -f http://localhost:8080/actuator/health || exit 1

# Run Spring Boot
ENTRYPOINT ["java", "-jar", "app.jar"]

# Build stage
FROM gradle:8.5-jdk21 AS build
WORKDIR /app
COPY build.gradle settings.gradle ./
COPY gradle ./gradle
COPY src ./src
RUN gradle build -x test --no-daemon

# Runtime stage
FROM eclipse-temurin:21-jre
WORKDIR /app

# Add a non-root user for security
RUN groupadd -r spring && useradd -r -g spring spring
USER spring:spring

# Copy the built jar from the build stage
COPY --from=build /app/build/libs/UserService-0.0.1-SNAPSHOT.jar app.jar

# Expose the port
EXPOSE 9810

# Health check
HEALTHCHECK --interval=30s --timeout=10s --start-period=60s --retries=3 \
    CMD curl -f http://localhost:9810/actuator/health || exit 1

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
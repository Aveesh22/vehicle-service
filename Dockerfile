# Use Java 21
FROM openjdk:21-jdk-slim

# Set the working directory in the container
WORKDIR /app

# Copy the JAR file to the container
COPY target/vehicle-service-*.jar vehicle-service.jar

# Expose the port your application runs on
EXPOSE 8080

# Disable Spring DevTools restart feature explicitly
ENV SPRING_DEVTOOLS_RESTART_ENABLED=false

# Command to run the application
ENTRYPOINT ["java", "-jar", "vehicle-service.jar"]
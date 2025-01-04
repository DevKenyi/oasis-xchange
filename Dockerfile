# Use an official OpenJDK runtime as the base image
FROM openjdk:17-jdk-alpine

# Set the working directory in the container
WORKDIR /app

# Copy the JAR file into the container
COPY target/oasis-xchanger.jar oasis-xchanger.jar

# Expose the port your app runs on
EXPOSE 9092  # Updated to match server.port in application.yml

# Command to run your application
ENTRYPOINT ["java", "-jar", "oasis-xchanger.jar"]
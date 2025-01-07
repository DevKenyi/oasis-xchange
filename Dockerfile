FROM openjdk:17-jdk-alpine

# Set the working directory in the container
WORKDIR /app

# Copy the JAR file into the container (Make sure the path is correct)
COPY target/OasisMedicXchange-0.0.1-SNAPSHOT.jar OasisMedicXchange-0.0.1-SNAPSHOT.jar

# Expose the port your app runs on
EXPOSE 9092

# Command to run your application
ENTRYPOINT ["java", "-jar", "OasisMedicXchange-0.0.1-SNAPSHOT.jar"]

# Use an official Java runtime as a parent image
FROM openjdk:17-jdk-slim

# Set the working directory in the container
WORKDIR /ADPProject/backend

# Copy the Gradle build file and source code
COPY build.gradle settings.gradle ./
COPY src ./src

# Copy the Gradle wrapper files (if using the wrapper)
COPY gradlew ./
COPY gradle ./gradle/

# Install dependencies and build the application
RUN ./gradlew build -x test

# Copy the built JAR file into the image
# Adjust the path if your JAR file is located in a different directory
COPY build/libs/*.jar app.jar

# Expose the port the app runs on
EXPOSE 8080

# Command to run the application
ENTRYPOINT ["java", "-jar", "app.jar"]

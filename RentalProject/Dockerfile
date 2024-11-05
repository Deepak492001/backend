# Stage 1: Build the project using Maven
FROM maven:3.8.5-openjdk-17 AS build
WORKDIR /app

# Copy only the pom.xml and download dependencies to cache them
COPY pom.xml .
RUN mvn dependency:go-offline

# Copy the entire source code and build the project
COPY src src
RUN mvn clean package -DskipTests

# Stage 2: Create a lightweight image with the built JAR file
FROM openjdk:17.0.1-jdk-slim
WORKDIR /app

# Copy the JAR file from the build stage
COPY --from=build /app/target/*.jar RentalProject.jar
# Expose the necessary port
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "RentalProject.jar"]

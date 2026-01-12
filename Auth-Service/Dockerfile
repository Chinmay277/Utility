# ----------------------------------------------
# Step 1: Build the application using Maven
# ----------------------------------------------

FROM maven:3.9.5-eclipse-temurin-21 AS build
WORKDIR /app

COPY pom.xml .
RUN mvn dependency:go-offline

COPY src ./src
RUN mvn clean package -DskipTests

# ----------------------------------------------
# Step 2: Set up runtime environment
# ----------------------------------------------

FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

# copy the packaged jar file from the build stage
COPY --from=build /app/target/auth-service.jar auth-service.jar

# expose the port the application runs on
EXPOSE 9000

# command to run the application
ENTRYPOINT ["java","-jar","auth-service.jar"]

# docker build -t auth-service:latest .
# docker run -p 8083:8083 auth-service:latest
# ----------------------------------------------
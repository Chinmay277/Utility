# ----------------------------------------------
# Step 1: Build the application using Maven
# ----------------------------------------------

FROM maven:3.9.5-eclipse-temurin-21 AS build
WORKDIR /app

# copy pom.xml
COPY pom.xml .
RUN mvn dependency:go-offline

# copy source code into the container
COPY src ./src

# package the application
RUN mvn clean package -DskipTests

# ----------------------------------------------
# Step 2: Set up runtime environment
# ----------------------------------------------

FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

# copy the packaged jar file from the build stage
COPY --from=build /app/target/api-gateway.jar api-gateway.jar

# expose the port the application runs on
EXPOSE 8081

# command to run the application
ENTRYPOINT ["java","-jar","api-gateway.jar"]

# docker build -t api-gateway:latest .
# docker run -p 8080:8080 api-gateway:latest
# ----------------------------------------------
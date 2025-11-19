# ----------------------------------------------
# Step 1: Build the application using Maven
# ----------------------------------------------
	
# set up maven build environment
FROM maven:3.9.5-eclipse-temurin-21 AS build
# set working directory
WORKDIR app
# copy pom.xml
COPY pom.xml .
# download all dependencies
RUN mvn dependency:go-offline
# copy source code into the container
COPY src ./src
# package the application
RUN mvn clean package -DskipTests

# ----------------------------------------------
	
# ----------------------------------------------	
# Step 2: Set up runtime environment
# ----------------------------------------------

# set up java env	
FROM eclipse-temurin:21-jre-alpine
# set working directory
WORKDIR app
# copy the packaged jar file from the build stage
COPY --from=build /app/target/notification-service-1.0.jar notification-service-1.0.jar
# expose the port the application runs on
EXPOSE 8084
# command to run the application
ENTRYPOINT ["java","-jar","notification-service-1.0.jar"]
# docker build -t notification-service:latest .
# docker run -p 8084:8084 notification-service:latest
# ----------------------------------------------

	
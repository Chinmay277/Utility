#Part 1---Set up maven build environment

FROM maven:3.9.5-eclipse-temurin-21 AS build

#Set working directory
WORKDIR app
#Copy pom.xml
COPY pom.xml .
#Download all dependencies
RUN mvn dependency:go-offline
#Copy source code into the container
COPY src ./src
#Package the application
RUN mvn clean package -DskipTests

#Part 2---Set up runtime environment
FROM eclipse-temurin:21-jre-alpine
#Set working directory
WORKDIR app
#Copy the packaged jar file from the build stage
COPY --from=build /app/target/work-order-service-1.0.jar work-order-service-1.0.jar
#Expose the port the application runs on
EXPOSE 8083
#Command to run the application
ENTRYPOINT ["java","-jar","work-order-service-1.0.jar"]
#docker build -t work-order-service:latest .
#docker run -p 8083:8083 work-order-service:latest

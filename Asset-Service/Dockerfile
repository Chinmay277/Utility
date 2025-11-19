# Part 1 Setup the environment to build the application
# Maven Build, import maven image to build the application

FROM maven:3.9.5-eclipse-temurin-21 AS build

#Set working directory
WORKDIR app

#Copy pom.xml 
COPY pom.xml .

#Download all the dependencies

RUN mvn dependency:go-offline

#Copy the source code into the container

COPY src ./src

#Package the application

RUN mvn clean package -DskipTests

#-----------------------------------------------------------------------------------------------------------------------------

#Part 2 Step to run the application

#Run the application
FROM eclipse-temurin:21-jre-alpine

#Set working directory
WORKDIR app

#Copy the packaged jar file from the build stage
COPY --from=build /app/target/asset-service-1.0.jar asset-service-1.0.jar

#Expose the port the application runs on
EXPOSE 8082

#Command to run the application
ENTRYPOINT ["java","-jar","asset-service-1.0.jar"]
#docker build -t asset-service:latest .
#docker run -p 8082:8082 asset-service:latest


# AS <NAME> to name this stage as maven
FROM maven:3.8.5 AS maven

WORKDIR /usr/src/app
COPY . /usr/src/app
# Compile and package the application to an executable JAR
RUN mvn package -DskipTests

# For Java 16, 
FROM openjdk:17-alpine

ARG JAR_FILE=treasure_hunt-0.0.1-SNAPSHOT.jar

WORKDIR /opt/app

# Copy the treasure_hunt.jar from the maven stage to the /opt/app directory of the current stage.
COPY --from=maven /usr/src/app/target/${JAR_FILE} /opt/app/

EXPOSE 8080

ENTRYPOINT ["java","-jar","treasure_hunt-0.0.1-SNAPSHOT.jar"]

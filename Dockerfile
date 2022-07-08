FROM openjdk:17-jdk-alpine
COPY ${JAR_FILE} app.jar

WORKDIR /usr/app

EXPOSE 8080
ENTRYPOINT ["java","-jar","/app.jar"]


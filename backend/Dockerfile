FROM openjdk:17-jdk-alpine

RUN  ./mvnw clean package

ARG JAR_FILE=target/*.jar

COPY ${JAR_FILE} app.jar

EXPOSE 8000

ENTRYPOINT ["java","-jar","/app.jar"]
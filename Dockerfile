FROM maven:latest AS build

WORKDIR /app

COPY pom.xml .
COPY src/main/java/ ./src/main/java/
COPY src/main/resources/ ./src/main/resources/

RUN mvn clean -DskipTests=true package

FROM openjdk:17-jdk-slim
COPY --from=build /app/target/gym_app-1.0-SNAPSHOT.jar /app/my-app.jar
EXPOSE 8080
WORKDIR /app

CMD ["java", "-jar", "my-app.jar"]



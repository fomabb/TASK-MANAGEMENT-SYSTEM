FROM maven:3.8-amazoncorretto-21-al2023 AS builder
WORKDIR /app

COPY pom.xml ./pom.xml
COPY src ./src

RUN mvn clean package -Dmaven.test.skip=true

FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

COPY --from=builder /app/target/*.jar /app/*.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app/*.jar"]
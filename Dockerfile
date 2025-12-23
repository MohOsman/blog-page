FROM maven:3.9-eclipse-temurin-25 AS builder

WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

FROM eclipse-temurin:25-jre

WORKDIR /app
COPY --from=builder /app/target/page-0.0.1-SNAPSHOT.jar app.jar



ENTRYPOINT ["java", "-jar", "app.jar"]


FROM maven:3.9-eclipse-temurin-21 AS build
WORKDIR /build
COPY . .
RUN mvn clean package -DskipTests

FROM eclipse-temurin:21-jdk-jammy
WORKDIR /app

COPY --from=build /build/target/app.jar app.jar

ENTRYPOINT ["java", "-jar", "/app/app.jar"]

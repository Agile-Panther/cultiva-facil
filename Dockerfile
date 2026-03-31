FROM maven:3.9-eclipse-temurin-21 AS build
WORKDIR /app
COPY pom.xml .
COPY domain/pom.xml domain/
COPY application/pom.xml application/
COPY infrastructure/pom.xml infrastructure/
COPY presentation-backend/pom.xml presentation-backend/
COPY presentation-frontend/pom.xml presentation-frontend/
RUN mvn dependency:go-offline
COPY domain/src domain/src
COPY application/src application/src
COPY infrastructure/src infrastructure/src
COPY presentation-backend/src presentation-backend/src
COPY presentation-frontend/src presentation-frontend/src
RUN mvn clean package -DskipTests

FROM eclipse-temurin:21-jre AS runtime
WORKDIR /app
COPY --from=build /app/presentation-backend/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
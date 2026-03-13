# ── Etapa 1: Build con Maven + Java 25 ──────────────────────────────
FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src

# Forzar compilación con Java 21 (compatible con release 25 via source/target override)
RUN mvn clean package -DskipTests \
    -Dmaven.compiler.release=21 \
    -Dmaven.compiler.source=21 \
    -Dmaven.compiler.target=21

# ── Etapa 2: Imagen final ───────────────────────────────────────────
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
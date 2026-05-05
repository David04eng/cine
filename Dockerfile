# Etapa de construcción
FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app

# Al estar en la raíz, solo copiamos el pom directamente
COPY pom.xml .
RUN mvn dependency:go-offline

# Copiamos el src de la raíz
COPY src ./src
RUN mvn clean package -DskipTests

# Etapa de ejecución
FROM eclipse-temurin:21-jdk-jammy
WORKDIR /app

# El JAR se genera en /app/target/ dentro del contenedor
COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080

# Límite de memoria para que Render no te lo mate
ENTRYPOINT ["java", "-Xmx300m", "-jar", "app.jar"]
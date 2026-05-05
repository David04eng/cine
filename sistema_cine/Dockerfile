# Etapa de construcción
FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app

# Copiamos los archivos DESDE la subcarpeta sistema_cine al contenedor
COPY sistema_cine/pom.xml .
RUN mvn dependency:go-offline

COPY sistema_cine/src ./src
RUN mvn clean package -DskipTests

# Etapa de ejecución
FROM eclipse-temurin:21-jdk-jammy
WORKDIR /app

# El JAR se genera en /app/target/ porque WORKDIR era /app
COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080

# Límite de memoria para el plan gratuito de Render
ENTRYPOINT ["java", "-Xmx300m", "-jar", "app.jar"]